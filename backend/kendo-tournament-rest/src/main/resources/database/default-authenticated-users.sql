---
-- #%L
-- Kendo Tournament Manager (Rest)
-- %%
-- Copyright (C) 2021 - 2022 Softwaremagico
-- %%
-- This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
-- <softwaremagico@gmail.com> Valencia (Spain).
--  
-- This program is free software; you can redistribute it and/or modify it under
-- the terms of the GNU General Public License as published by the Free Software
-- Foundation; either version 2 of the License, or (at your option) any later
-- version.
--  
-- This program is distributed in the hope that it will be useful, but WITHOUT
-- ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
-- FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
-- details.
--  
-- You should have received a copy of the GNU General Public License along with
-- this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
-- #L%
---
INSERT INTO authenticated_users (id, username, password, full_name) VALUES (1, 'admin@test.com', '$2a$12$hawW3GfY4/Ib/1.9KdVvVObw2t4FsXjkYApy5xlJf.P5GO3K72OSm', 'Admin User');
INSERT INTO authenticated_user_roles (authenticated_user, roles) VALUES (1, 'admin');
INSERT INTO authenticated_user_roles (authenticated_user, roles) VALUES (1, 'viewer');
