INSERT IGNORE INTO customers (id, company_id, name, phone) VALUES
  (1, 1, '王小姐', '0912000001'),
  (2, 1, '陳先生', '0912000002'),
  (3, 2, '林太太', '0912000003');

INSERT IGNORE INTO addresses (id, customer_id, city, line, pings) VALUES
  (1, 1, '台北市', '大安區復興南路一段 100 號 5 樓', 28),
  (2, 2, '新北市', '板橋區文化路二段 50 號', 35),
  (3, 3, '桃園市', '中壢區中正路 200 號', 22);

INSERT IGNORE INTO bookings (id, company_id, customer_id, address_id, service_type, preferred_at, quoted_amount, status) VALUES
  (1, 1, 1, 1, 'MOLD_REMOVAL', '2026-12-01 09:00:00', 6800, 'CONFIRMED'),
  (2, 1, 2, 2, 'GENERAL_CLEANING', '2026-12-02 14:00:00', 4200, 'QUOTED'),
  (3, 2, 3, 3, 'MOVE_OUT_CLEANING', '2026-12-03 10:00:00', NULL, 'DRAFT');
