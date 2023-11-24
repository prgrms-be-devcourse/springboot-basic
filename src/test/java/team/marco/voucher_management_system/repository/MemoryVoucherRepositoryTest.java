package team.marco.voucher_management_system.repository;

import team.marco.voucher_management_system.console_app.repository.MemoryVoucherRepository;

class MemoryVoucherRepositoryTest extends VoucherRepositoryTest {
    protected VoucherRepository getRepository() {
        return new MemoryVoucherRepository();
    }
}
