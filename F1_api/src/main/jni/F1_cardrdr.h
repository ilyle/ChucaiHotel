#ifndef _F1_CARDRDR_H
#define _F1_CARDRDR_H
#include <jni.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>
#include <fcntl.h>
#include <termios.h>
#include <memory.h>
#include <pthread.h>
#include <sys/stat.h>
#include <assert.h>
#include "android/log.h"

#define TAG "F1jni"
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, TAG, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, TAG, fmt, ##args)

typedef unsigned char BYTE;

class F1CardReader
{
public:
    F1CardReader();
    ~F1CardReader();

    int connect(char *portName, speed_t speed,int address);
    int disconnect();
    int execute(BYTE *cmdData, int cmdLength, bool bFlg, int respOff);

	pthread_mutex_t mutex;
	BYTE *respData;
	int respLength;

private:
    int sendCommand(BYTE *cmdData, int cmdLength);
    int recvACK();
    int sendENQ();
    int recvResponse(bool bGetDataFlg, int respOff);
    int configPort(int fd, speed_t speed);
    int readData(BYTE *buffer, int bytesToRead, fd_set *fds);
    int writeData(BYTE *buffer, int bytesToWrite);
    int waitBytesAvailable(int timeOut, fd_set *fds);
    BYTE getBCC(BYTE *buffer, int length);
    void logCommData(bool isSend, BYTE *buffer, int length);

private:
    enum {
    	MAX_MSGSIZE = 65536,
     	MIN_MSGSIZE = 7
    };

	BYTE m_buffer[MAX_MSGSIZE];
    int m_fd;
    int m_address;

};

#endif