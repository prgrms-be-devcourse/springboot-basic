package team.marco.vouchermanagementsystem.repository;

class MemoryVoucherRepositoryTest extends VoucherRepositoryTest {
    protected VoucherRepository getRepository() {
        return new MemoryVoucherRepository();
    }
}
