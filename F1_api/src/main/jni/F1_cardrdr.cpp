#include "F1_cardrdr.h"
#include "F1_errs.h"

// 传输控制字符
//
#define ACK		0x06
#define NAK		0x15
#define ENQ		0x05
#define STX		0x02
#define ETX		0x03
#define EOT		0x04


static __inline__ int __tcgetattr(int fd, termios *s)
{
    return ioctl(fd, TCGETS, s);
}

static __inline__ int __tcsetattr(int fd, int __opt, const termios *s)
{
    return ioctl(fd, __opt, (void *)s);
}

static __inline__ int __tcflush(int fd, int __queue)
{
    return ioctl(fd, TCFLSH, (void *)(intptr_t)__queue);
}

static __inline__ speed_t __cfgetospeed(const termios *s)
{
    return (speed_t)(s->c_cflag & CBAUD);
}

static __inline__ int __cfsetospeed(termios *s, speed_t  speed)
{
    s->c_cflag = (s->c_cflag & ~CBAUD) | (speed & CBAUD);
    return 0;
}

static __inline__ speed_t __cfgetispeed(const termios *s)
{
    return (speed_t)(s->c_cflag & CBAUD);
}

static __inline__ int __cfsetispeed(termios *s, speed_t  speed)
{
    s->c_cflag = (s->c_cflag & ~CBAUD) | (speed & CBAUD);
  return 0;
}

static __inline__ void __cfmakeraw(termios *s)
{
    s->c_iflag &= ~(IGNBRK|BRKINT|PARMRK|ISTRIP|INLCR|IGNCR|ICRNL|IXON);
    s->c_oflag &= ~OPOST;
    s->c_lflag &= ~(ECHO|ECHONL|ICANON|ISIG|IEXTEN);
    s->c_cflag &= ~(CSIZE|PARENB);
    s->c_cflag |= CS8;
}

static __inline__ char hexChar(BYTE digit)
{
	if (digit <= 9)
		return '0' + digit;
	else
		return 'A' + (digit - 10);
}

F1CardReader::F1CardReader()
{
    pthread_mutex_init(&mutex,NULL);
    LOGD("R1CardReader::R1CardReader");
}


F1CardReader::~F1CardReader()
{
    pthread_mutex_destroy(&mutex);
    LOGD("R1CardReader::~R1CardReader");
}

int F1CardReader::connect(char *portName, speed_t speed ,int address)
{
    m_fd = open(portName, O_RDWR | O_NOCTTY | O_NDELAY);
    if (m_fd == -1){
        LOGE("open failed: %s", portName);
        return E_PORT_NOT_AVAIL;
    }
    if (configPort(m_fd, speed) == -1){
    	close(m_fd);
    	return E_PORT_NOT_AVAIL;
    }

	fcntl(m_fd, F_SETFL, 0);

	m_address =  address;
	BYTE cmdData[] = { 'R', 'S'};
	int ret = execute(cmdData, sizeof(cmdData), 0, 0);
	LOGE("ret0: %d  address = %d", ret,address);
	if (ret != 0){
        close(m_fd);
        return ret;
    }
    return 0;
}

int F1CardReader::disconnect()
{
    LOGD("R1CardReader::disconnect");
    close(m_fd);
    return 0;
}

int F1CardReader::execute(BYTE *cmdData, int cmdLength, bool bFlg, int respOff)
{
	int ret;
	LOGD("R1CardReader::execute");
	ret = sendCommand(cmdData, cmdLength);
	LOGE("ret1: %d", ret);
	if (ret != 0){
	        ret = E_SENDDATA_ERROR;
			return ret;
	}
	ret = recvACK();
	if (ret != 0) {
	        ret = E_REVICE_ERROR;
			return ret;
	}
	ret = sendENQ();
	 LOGE("ret3: %d", ret);
	if (ret != 0){
	        ret = E_SENDDATA_ERROR;
			return ret;
	}
	ret = recvResponse(bFlg, respOff);
    LOGE("ret4: %d", ret);
	return ret;
}

int F1CardReader::sendCommand(BYTE *cmdData, int cmdLength)
{
	assert(cmdData != NULL);
	assert(cmdLength >= 2);
    int length = 0;

	m_buffer[length++] = STX;
	if ( m_address < 0xff)
	{
        m_buffer[length++] = (m_address / 10) + 0x30;
        m_buffer[length++] = (m_address % 10) + 0x30;
	}
	memcpy(&m_buffer[length], cmdData, cmdLength);
	length += cmdLength;
	m_buffer[length++] = ETX;
	length++;
	m_buffer[length - 1] = getBCC(m_buffer, length);
	return writeData(m_buffer, length);
}

int F1CardReader::recvACK()
{
	int ret;
	BYTE b[3];
	fd_set fds;
	ret = waitBytesAvailable(1000, &fds);
	if (ret != 0) {
		if (ret == E_TIMEOUT)
			return E_DEV_NOT_READY;
		return ret;
	}
	ret = readData(b, m_address == 0xff?1:3, &fds);
	if (ret != 0) return ret;
	if (b[0] != ACK)
		return E_INTERNAL_ERROR;
    return 0;
}

int F1CardReader::sendENQ()
{
	BYTE b[] = {ENQ,(BYTE)((m_address / 10) + 0x30,(m_address % 10) + 0x30)};
	return writeData(b,m_address == 0xff?1:3);
}

int F1CardReader::recvResponse(bool bGetDataFlg, int respOff)
{
	int ret;
	int length = 0;
	BYTE bRevb[1],bb;
	fd_set fds;
    if (bGetDataFlg){
      ret = waitBytesAvailable(7000, &fds);
	  if (ret != 0) return ret;

        ret = readData(m_buffer, 3, &fds);
        if (ret != 0)  return ret;
        length = 3;
        while(true){

         ret = readData(bRevb,1, &fds);
         if (ret != 0)  return ret;
         m_buffer[length++] = bRevb[0];

         bb = getBCC(m_buffer, length);
         //LOGE("recvResponse : bb =   %02x %02x ", bb,m_buffer[length - 1]);
         if (bb == m_buffer[length - 1]) break;
        }
       respData = &m_buffer[ m_address == 0xff ? 3 : 5];
       respLength = length - (m_address == 0xff ?5 : 7);

    }
	return 0;
}

int F1CardReader::configPort(int fd, speed_t speed)
{
	termios options;

    LOGD("R1CardReader::configPort : fd = %d", fd);

	if (__tcgetattr(fd, &options)){
	    LOGD("__tcgetattr failed");
		return -1;
	}

	if (__cfsetispeed(&options, speed)){
	    LOGD("__cfsetispeed failed");
		return -1;
	}

	if (__cfsetospeed(&options, speed)){
	    LOGD("__cfsetospeed failed");
		return -1;
	}

	__cfmakeraw(&options);

    if (__tcsetattr(fd, TCSETS, &options)){
	    LOGD("__tcsetattr failed");
		return -1;
	}

	return 0;
}

int F1CardReader::readData(BYTE *buffer, int bytesToRead, fd_set *fds)
{
	int count = 0;
	int bytesRead;

	while (count < bytesToRead) {
		if (FD_ISSET(m_fd, fds)){
			bytesRead = read(m_fd, &buffer[count], bytesToRead - count);
			count += bytesRead;
		}
		else {
			LOGE("internal error: FS_ISSET");
			return E_INTERNAL_ERROR;
		}
	}
	logCommData(false, buffer, bytesToRead);
	return 0;
}

int F1CardReader::writeData(BYTE *buffer, int bytesToWrite)
{
    int bytesWritten = write(m_fd, buffer, bytesToWrite);
    if (bytesWritten != bytesToWrite)
        return E_INTERNAL_ERROR;
	logCommData(true, buffer, bytesToWrite);
    return 0;
}

int F1CardReader::waitBytesAvailable(int timeOut, fd_set *fds)
{
 int nfds;
	timeval tv;

	tv.tv_sec = 10;
	tv.tv_usec = timeOut * 100;

	FD_ZERO(fds);
	FD_SET(m_fd, fds);

	nfds = select(m_fd + 1, fds, NULL, NULL, &tv);
	if (nfds < 0)
		return E_INTERNAL_ERROR;
	if (nfds == 0)
		return E_TIMEOUT;
	return 0;
}

BYTE F1CardReader::getBCC(BYTE *buffer, int length)
{
	BYTE bcc = 0;
    	for (int i = 0; i < length - 1; i++)
    		bcc ^= buffer[i];
    return bcc;
}


void F1CardReader::logCommData(bool isSend, BYTE *buffer, int length)
{
    char *hstr = (char*)malloc(length * 3 + 1);
    if (hstr != NULL) {
        for (int i = 0; i < length; i++ ) {
            hstr[i * 3] = hexChar(buffer[i] >> 4);
            hstr[i * 3 + 1] = hexChar(buffer[i] % 16);
            hstr[i * 3 + 2] = ' ';
        }
	    hstr[length * 3] = 0;

	    LOGD("%s : %s\n", isSend ? "S" : "R", hstr);
        free(hstr);
	}
}