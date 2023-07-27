package s3.s3.dto.factory;

import s3.s3.dto.UploadFileResult;

public class UploadFileResultFactory {
    public static UploadFileResult success(String fileName)
    {
        var result = new UploadFileResult();
        result.isSuccess = true;
        result.fileUrl = fileName;
        return result;
    }

    public static UploadFileResult error()
    {
        var result = new UploadFileResult();
        result.isSuccess = false;
        return result;
    }
}
