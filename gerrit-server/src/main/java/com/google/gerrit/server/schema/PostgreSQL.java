// Copyright (C) 2012 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.server.schema;

import static com.google.gerrit.server.schema.JdbcUtil.hostname;
import static com.google.gerrit.server.schema.JdbcUtil.port;

import com.google.gerrit.server.config.ConfigSection;
import com.google.gerrit.server.config.GerritServerConfig;
import com.google.inject.Inject;
import java.io.IOException;
import org.eclipse.jgit.lib.Config;

class PostgreSQL extends BaseDataSourceType {

  private Config cfg;

  @Inject
  PostgreSQL(@GerritServerConfig final Config cfg) {
    super("org.postgresql.Driver");
    this.cfg = cfg;
  }

  @Override
  public String getUrl() {
    final StringBuilder b = new StringBuilder();
    final ConfigSection dbc = new ConfigSection(cfg, "database");
    b.append("jdbc:postgresql://");
    b.append(hostname(dbc.optional("hostname")));
    b.append(port(dbc.optional("port")));
    b.append("/");
    b.append(dbc.required("database"));
    return b.toString();
  }

  @Override
  public ScriptRunner getIndexScript() throws IOException {
    return getScriptRunner("index_postgres.sql");
  }
}
