package programmers.org.voucher.repository.util.statement.delete;

import programmers.org.voucher.repository.util.constant.Table;

public class Delete {

    private Table table;

    public Delete(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }
}
