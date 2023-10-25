package study.dev.spring.global.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlFixture {

	public static final String DDL = """
		CREATE TABLE Voucher(
		    uuid           VARCHAR(36)  PRIMARY KEY ,
		    name           VARCHAR(255) NOT NULL UNIQUE,
		    voucherType    VARCHAR(255) NOT NULL,
		    discountAmount DOUBLE       NOT NULL
		);
				
		CREATE TABLE Customer
		(
		    uuid           VARCHAR(36)  PRIMARY KEY,
		    name           VARCHAR(255) NOT NULL
		);
				
		CREATE TABLE Wallet(
		    uuid       VARCHAR(36) PRIMARY KEY,
		    customerId VARCHAR(36),
		    voucherId  VARCHAR(36),
		    FOREIGN KEY (customerId) REFERENCES Customer (uuid),
		    FOREIGN KEY (voucherId) REFERENCES Voucher(uuid) ON DELETE CASCADE
		);
	""";
}
