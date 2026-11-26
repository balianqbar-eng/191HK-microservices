INSERT IGNORE INTO technicians (id, company_id, name) VALUES
  (1, 1, '阿明'),
  (2, 1, '阿華'),
  (3, 2, '小李');

INSERT IGNORE INTO jobs (id, company_id, booking_id, scheduled_at, status, technician_id) VALUES
  (1, 1, 1, '2026-12-01 09:00:00', 'ASSIGNED', 2),
  (2, 1, 2, '2026-12-02 14:00:00', 'PENDING', NULL),
  (3, 2, 3, '2026-12-03 10:00:00', 'PENDING', NULL);

INSERT IGNORE INTO job_assignments (id, job_id, from_technician_id, to_technician_id, reason, assigned_at) VALUES
  (1, 1, NULL, 1, '首次派工', '2026-11-26 10:00:00'),
  (2, 1, 1, 2, '阿明當天請假，改派阿華', '2026-11-26 15:30:00');
