#include "F1_cardrdr.h"
#include "F1_errs.h"

class AutoLock
{
private:
    pthread_mutex_t *m_lptx;
    bool m_auto;

public:
    AutoLock(pthread_mutex_t *lptx, bool bauto = true)
        : m_lptx(lptx), m_auto(bauto) {
        pthread_mutex_lock( m_lptx );
    }

    ~AutoLock() {
    	if (m_auto)
        	pthread_mutex_unlock( m_lptx );
    }

    void release() {
    	pthread_mutex_unlock( m_lptx );
    }
};

static speed_t getBaudrate(jint baudrate)
{
	switch(baudrate) {
	case 9600: return B9600;
	case 19200: return B19200;
	case 38400: return B38400;
	case 57600: return B57600;
	case 115200: return B115200;
	default: return -1;
	}
}

static jint setRespData(JNIEnv *env, jobject rdt, jbyte *rdtPtr, int rdtLen)
{
	jclass cls;
	jfieldID fid;
	jbyteArray ba;

	cls = env->FindClass("com/act/F1_api/F1CardReader$RDATA");
	if (cls == NULL)
		return E_INTERNAL_ERROR;
	fid = env->GetFieldID(cls, "buffer", "[B");
	if (fid == NULL)
		return E_INTERNAL_ERROR;

	ba = env->NewByteArray(rdtLen);
	if (ba == NULL)
		return E_NO_MEMORY;
	env->SetByteArrayRegion(ba, 0, rdtLen, rdtPtr);
	env->SetObjectField(rdt, fid, ba);

	return 0;
}

F1CardReader *cr = NULL;

extern "C" JNIEXPORT jint JNICALL Java_com_act_F1_1api_F1CardReader_csConnect
    (JNIEnv *env, jobject thiz, jstring portName, jint baudRate, jint address)
{
	char *name = NULL;
	jint  ret;
	speed_t speed;
    name = (char*)env->GetStringUTFChars(portName, 0);
    if (name == NULL) {
        ret = E_NO_MEMORY;
        goto EXIT;
    }
	speed = getBaudrate(baudRate);
	if (speed == -1) {
		ret = E_SEEPD_ERROR;
		goto EXIT;
	}

	cr = new F1CardReader();
	if (cr == NULL) {
        ret = E_NO_MEMORY;
        goto EXIT;
    }
    ret = cr->connect(name, speed,address);

    if (ret != 0) {
    	delete cr;
    	goto EXIT;
    }
	//ret =   (long)cr;
	LOGD(" cr->connect2 ：ret = %ld ",ret);
EXIT:
    if (name != NULL)
        env->ReleaseStringUTFChars(portName, name);
    return ret;
}

extern "C" JNIEXPORT jint JNICALL Java_com_act_F1_1api_F1CardReader_csDisconnect
    (JNIEnv *env, jobject thiz, jint handle)
{
	//R1CardReader *cr;
	jint ret;
	if (cr == 0)
	return E_NOT_CONNECTED;

	AutoLock lock(&cr->mutex, false);
	cr->disconnect();
	lock.release();
	delete cr;
	return 0;
}

extern "C" JNIEXPORT jint JNICALL Java_com_act_F1_1api_F1CardReader_csExecute
    (JNIEnv *env, jobject thiz, jint handle, jbyteArray cdt, jint stOff, jint resOff, jobject rdt, jint expectedMinLen)
{
	jbyte *cdtPtr = NULL;
	int cdtLen = 0;
	long ret = 0;
	bool bFlg  = true;

	if (cr == 0)
	 return E_NOT_CONNECTED;

	AutoLock lock(&cr->mutex);

	cdtLen = env->GetArrayLength(cdt);
	cdtPtr = env->GetByteArrayElements(cdt, NULL);
	if (cdtPtr == NULL){
		ret = E_INTERNAL_ERROR;
		goto EXIT;
	}
	if (rdt == NULL) bFlg = false;
	ret = cr->execute((BYTE*)cdtPtr, cdtLen, bFlg, resOff);
	if (ret != 0)
		goto EXIT;

	if (rdt != NULL) {
		if (cr->respLength < expectedMinLen) {
    		ret = E_INTERNAL_ERROR;
    		goto EXIT;
    	}
		ret = setRespData(env, rdt, (jbyte*)cr->respData, (jint)cr->respLength);
	}

EXIT:
	if (cdtPtr != NULL)
		env->ReleaseByteArrayElements(cdt, cdtPtr, 0);
    return ret;
}