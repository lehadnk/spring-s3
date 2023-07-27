package s3.s3.business;

public class PathConcatenator {
    public String concatPaths(String path1, String path2)
    {
        var result = path1.trim();
        if (!result.endsWith("/")) {
            result += "/";
        }

        if (path2.startsWith("/")) {
            result += path2.substring(1);
        } else {
            result += path2;
        }

        return result;
    }
}
