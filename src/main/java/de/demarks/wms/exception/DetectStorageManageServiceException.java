package de.demarks.wms.exception;

public class DetectStorageManageServiceException extends BusinessException{

    DetectStorageManageServiceException(){
        super();
    }

    public DetectStorageManageServiceException(Exception e){
        super(e);
    }

    DetectStorageManageServiceException(Exception e, String exceptionDesc){
        super(e, exceptionDesc);
    }
}
