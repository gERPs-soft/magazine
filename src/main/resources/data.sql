INSERT INTO product_group(id, name) VALUES
(1, 'Papiery toaletowe'),
(2, 'Ręczniki papierowe'),
(3, 'Mydła'),
(4, 'Swish'),
(5, 'Podajniki papieru'),
(6, 'Podajniki ręczników');

/*INSERT INTO package_unit(id, name) VALUES
(1, 'Karton'),
(2, 'Folia'),
(3, 'Paleta'),
(4, 'Box');*/

INSERT INTO suppliers(id, name, city, street, street_number, post_code, phone_number, email, www, representative_person, bank_supplier_name, bank_supplier_account_number)
VALUES
(1, 'Flesz Sp. z o.o.', 'Warszawa', 'Cieślewskich', '25F', '03-017', 224238888, 'zamowienia@flesz.net.pl', 'www.flesz.net.pl', 'Maciej Russek', 'Santander Bank', '10204000001234885511'),
(2, 'Filar', 'Włocławek', 'Rolna', '121', '25-001', 221234567, 'g-jozef@wp.pl', 'www.filar.net.pl', 'Wiesław Filar', 'Santander Bank', '30104000001234885511'),
(3, 'Wiromix', 'Legionowo', 'Żeromskiego', '9', '04-020', 220022456, 'g-jozef@wp.pl', 'www.wiromix.net.pl', 'Lucyna Kowalska', 'Santander Bank', '12004000001234885511'),
(4, 'AS', 'Otwock', 'Ludna', '5', '12-017', 121230022, 'g-jozef@wp.pll', 'www.as-mydla.net.pl', 'Stanislaw Zielony', 'Santander Bank', '20208000001234885511');

INSERT INTO products(id, assort_index, name, product_group_id, unit_of_masure, barcode, weight_unit, package_unit, number_in_package, height, weight, length, number_in_pallet, supplier_id, stock, price, vat)
VALUES
(1, '1.02.003', 'Ręczniki PREMIUM 4000 biale', 2, 'szt', '112200445511', 8.2, 'Karton', 1, 50, 40, 60, 32, 1, 320, 49.00, 'VAT_23'),
(2, '1.07.001', 'Papier PROFIT Flesz 2w bialy', 1, 'szt', '112200444412', 4.0, 'Folia', 12, 20, 40, 60, 720, 2, 2400, 2.50, 'VAT_23');
