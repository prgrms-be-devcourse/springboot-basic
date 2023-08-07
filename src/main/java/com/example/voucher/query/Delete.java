package com.example.voucher.query;

import com.example.voucher.query.marker.Entity;

public class Delete {

    private final Class<? extends Entity> table;
    private final Where where;
    private final String query;

    private Delete(Class<? extends Entity> table, Where where) {
        this.table = table;
        this.where = where;
        query = makeQuery();
    }

    public String getQuery() {
        return query;
    }

    private String makeQuery() {
        String table = this.table.getSimpleName().toUpperCase();
        String where = this.where == null ? "" : this.where.getQuery();

        return String.format("DELETE FROM %s %s", table, where).trim();
    }

    public static DeleteCriteria builder() {
        return new DeleteCriteria();
    }

    public static class DeleteCriteria {

        private Class<? extends Entity> table;

        public WhereCriteria delete(Class<? extends Entity> table) {
            this.table = table;

            return new WhereCriteria(table);
        }

    }

    public static class WhereCriteria {

        private Class<? extends Entity> table;
        private Where where;

        public WhereCriteria(Class<? extends Entity> table) {
            this.table = table;
        }

        public Builder where(Where where) {
            this.where = where;

            return new Builder(table, where);
        }

        public Delete build() {
            return new Delete(table, where);
        }

    }

    public static class Builder {

        private Class<? extends Entity> table;
        private Where where;

        public Builder(Class<? extends Entity> table, Where where) {
            this.table = table;
            this.where = where;
        }

        public Delete build() {
            return new Delete(table, where);
        }

    }

}