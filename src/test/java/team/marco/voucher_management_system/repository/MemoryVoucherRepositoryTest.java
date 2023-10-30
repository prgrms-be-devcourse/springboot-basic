package team.marco.voucher_management_system.repository;

class MemoryVoucherRepositoryTest extends VoucherRepositoryTest {
    protected VoucherRepository getRepository() {
        return new MemoryVoucherRepository();
    }
}
