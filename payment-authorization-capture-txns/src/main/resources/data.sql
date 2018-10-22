-- Insert into payment table
insert into payment (id, currency, value, number, cvc, expiry_month, expiry_year, holder_name, merchant_account, reference) values (1, 'DLR', 3000, '3456', '456', '9', '2018',  'Prasad ',  'MASTER CARD', '1234');
insert into payment (id, currency, value, number, cvc, expiry_month, expiry_year, holder_name, merchant_account, reference) values (2, 'EUR', 3000, '1234', '345', '8', '2018',  'Li Wang ',  'DISCOVER', '2345');
-- Insert into capture_payment table
insert into capture_payment (id, merchant_account, currency, value, original_reference, reference) values (1, 'MASTER CARD', 'EUR', '500', 'PSPR-1011',  'YMR-1011')
