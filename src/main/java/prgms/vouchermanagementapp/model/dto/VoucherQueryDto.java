package prgms.vouchermanagementapp.model.dto;

public class VoucherQueryDto {

    public static class ByCreatedAt {
        private String start;
        private String end;

        public ByCreatedAt() {
        }

        public ByCreatedAt(String start, String end) {
            this.start = start;
            this.end = end;
        }

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }
    }
}
