package io.kroki.server.service;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.Utils;

import java.util.ArrayList;
import java.util.List;

public class HelloHandler {

  private final String rowTemplate;
  private final String tableTemplate;
  private final String pageTemplate;

  public HelloHandler() {
    this.rowTemplate = Utils.readResourceToBuffer("web/version_row.html").toString();
    this.tableTemplate = Utils.readResourceToBuffer("web/version_table.html").toString();
    String stylesheet = Utils.readResourceToBuffer("web/root/css/main.css").toString();
    String logo = Utils.readResourceToBuffer("web/root/assets/logo.svg").toString();
    this.pageTemplate = Utils.readResourceToBuffer("web/hello.html").toString()
      .replace("{stylesheet}", stylesheet)
      .replace("{logo}", logo);
  }

  public Handler<RoutingContext> create() {
    return routingContext -> {
      List<ServiceVersion> serviceVersions = new ArrayList<>();
      // QUESTION: should we dynamically fetch the versions ?
      serviceVersions.add(new ServiceVersion("actdiag", "0.5.4"));
      serviceVersions.add(new ServiceVersion("blockdiag", "1.5.4"));
      serviceVersions.add(new ServiceVersion("c4plantuml", "1.2019.9"));
      serviceVersions.add(new ServiceVersion("ditaa", "1.3.13"));
      serviceVersions.add(new ServiceVersion("erd", "0.1.3.0"));
      serviceVersions.add(new ServiceVersion("graphviz", "2.40.1"));
      serviceVersions.add(new ServiceVersion("mermaid", "8.4.3"));
      serviceVersions.add(new ServiceVersion("nomnoml", "0.6.1"));
      serviceVersions.add(new ServiceVersion("nwdiag", "1.0.4"));
      serviceVersions.add(new ServiceVersion("packetdiag", "1.0.4"));
      serviceVersions.add(new ServiceVersion("plantuml", "1.2019.9"));
      serviceVersions.add(new ServiceVersion("rackdiag", "1.0.4"));
      serviceVersions.add(new ServiceVersion("seqdiag", "0.9.6"));
      serviceVersions.add(new ServiceVersion("svgbob", "0.4.2"));
      serviceVersions.add(new ServiceVersion("umlet", "14.3.0"));
      serviceVersions.add(new ServiceVersion("vega", "5.9.1"));
      serviceVersions.add(new ServiceVersion("vegalite", "4.4.0"));
      String versionsTable = generateVersionsTable(serviceVersions);
      routingContext
        .response()
        .end(pageTemplate.replace("{versionsTable}", versionsTable));
    };
  }


  private String generateVersionsTable(List<ServiceVersion> serviceVersions) {
    StringBuilder sb = new StringBuilder();
    for (ServiceVersion status : serviceVersions) {
      sb.append(status.toHTML(rowTemplate)).append("\n");
    }
    return tableTemplate
      .replace("{tableBody}", sb.toString());
  }
}
