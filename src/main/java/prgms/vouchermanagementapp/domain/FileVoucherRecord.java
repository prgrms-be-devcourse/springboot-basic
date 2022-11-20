package prgms.vouchermanagementapp.domain;

public class FileVoucherRecord implements VoucherRecord {

    private final String filePath;

    public FileVoucherRecord(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public boolean isFile() {
        return true;
    }
}
