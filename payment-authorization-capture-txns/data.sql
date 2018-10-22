-- insert into authorize (Payment)
insert into table payment (number, expiryMonth, expiryYear, cvc, holderName, value, currency, reference, merchantAccount) values ('2345',   '9',   '2018',   '456',  'Wayne Lund' 3000,  'EUR', '1234E', 'MASTER CARD');
-- insert into table payment (number, expiryMonth, expiryYear, cvc, holderName, value, currency, reference, merchantAccount) values ('1234',   '8',   '2010',   '123',  'Li Wang', 4000, 'EUR', '1234E', 'DISCOVER');
-- insert into CapturePayment
insert into table capture_payment (original_reference, value, currency, reference, merchantAccount) values ('PSPR-1011', 500, 'EUR', 'YMR-1011',  'MASTER CARD');
-- insert into table capture_payment (original_reference, value, currency, reference, merchantAccount) values ('PSPR-1012', 600, 'EUR', 'YMR-1012',  'DISCOVER');
