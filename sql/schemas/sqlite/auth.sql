-- Basic Authentication Scheme. The intended use for this scheme is to authenticate
-- an user and check for privileges over an action. Check the auth documentation for
-- details.

-- Copyright (c) 2018 Juan Carlos Galan Hernandez
--
-- Permission is hereby granted, free of charge, to any person obtaining a copy
-- of this software and associated documentation files (the "Software"), to deal
-- in the Software without restriction, including without limitation the rights
-- to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
-- copies of the Software, and to permit persons to whom the Software is
-- furnished to do so, subject to the following conditions:
--
-- The above copyright notice and this permission notice shall be included in all
-- copies or substantial portions of the Software.
--
-- THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
-- IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
-- FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
-- AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
-- LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
-- OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
-- SOFTWARE.

DROP TABLE IF EXISTS AUser;
CREATE TABLE IF NOT EXISTS AUser (
   id       INTEGER PRIMARY KEY ASC AUTOINCREMENT
  ,username TEXT NOT NULL
  ,passwd   TEXT NOT NULL
  ,groupId  INTEGER NOT NULL
);

DROP TABLE IF EXISTS AGroup;
CREATE TABLE IF NOT EXISTS AGroup (
   id        INTEGER PRIMARY KEY ASC AUTOINCREMENT
  ,groupName TEXT NOT NULL
);

DROP TABLE IF EXISTS ExtraGroup;
CREATE TABLE IF NOT EXISTS ExtraGroup (
   id       INTEGER PRIMARY KEY ASC AUTOINCREMENT
  ,userId   INTEGER
  ,groupId  INTEGER
);

DROP TABLE IF EXISTS Permission;
CREATE TABLE IF NOT EXISTS Permission (
   id       INTEGER PRIMARY KEY ASC AUTOINCREMENT
  ,name     TEXT
  ,groupId  INTEGER
);