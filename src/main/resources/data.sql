INSERT INTO product_group(id, name) VALUES
(1, 'Papiery toaletowe'),
(2, 'Reczniki papierowe'),
(3, 'Mydla'),
(4, 'Swish'),
(5, 'Podajniki papieru'),
(6, 'Podajniki recznikow');

INSERT INTO suppliers(id, name, nip, city, street, street_number, post_code, phone_number, email, www, representative_person, bank_supplier_name, bank_supplier_account_number, active)
VALUES
(1, 'Flesz Sp. z o.o.', 1180742079, 'Warszawa', 'Cieślewskich', '25F', '03-017', 224238888, 'zamowienia@flesz.net.pl', 'www.flesz.net.pl', 'Maciej Russek', 'Santander Bank', '10204000001234885511', true ),
(2, 'FILAR FIJAŁKOWSCY Sp.j', 6661003530,'Zaryń', 'Sadlno', '46', '62-619', 221234567, 'g-jozef@wp.pl', 'www.filar.net.pl', 'Wiesław Filar', 'Santander Bank', '30104000001234885511', true),
(3, 'WYTWÓRNIA CHEMICZNA WIROMIX S C', 5361741996,'Legionowo', 'Adama Mickiewicza', '12A', '05-120', 220022456, 'g-jozef@wp.pl', 'www.wiromix.net.pl', 'Lucyna Kowalska', 'Santander Bank', '12004000001234885511', true),
(4, 'AS', 5321007014,'Otwock', 'Armii Krajowej', '5', '05-400', 121230022, 'g-jozef@wp.pll', 'www.as-mydla.net.pl', 'Stanislaw Zielony', 'Santander Bank', '20208000001234885511', true);

INSERT INTO products(id, assort_index, name, product_group_id, unit_of_masure, barcode, weight_unit, package_unit, number_in_package, height, weight, length, number_in_pallet, supplier_id, stock, price, vat)
VALUES
(1, '1.02.003', 'Reczniki PREMIUM 4000 biale', 2, 'szt', '112200445511', 8.2, 'Karton', 1, 50, 40, 60, 32, 1, 320, 49.00, 'VAT_23'),
(2, '1.07.001', 'Papier PROFIT Flesz 18 2w bialy', 1, 'szt', '112200444412', 6.0, 'Folia', 12, 20, 40, 60, 720, 2, 2400, 2.50, 'VAT_23'),
(3, '1.07.002', 'Papier PERFECT Flesz 18 2w celuloza', 1, 'szt', '112200444413', 6.0, 'Folia', 12, 20, 40, 60, 720, 2, 3600, 3.40, 'VAT_23'),
(4, '1.02.007', 'Reczniki PREMIUM 4000 szare', 2, 'szt', '112200444512', 8, 'Karton', 1, 50, 40, 60, 32, 2, 2400, 2.50, 'VAT_23');
