
/**
 * Copyright (c) 2007-2010, Fintan Fairmichael, University College Dublin under the BSD licence.
 * See LICENCE.TXT for details.
 *
 * This class is generated automatically from normal_classes.tpl.
 * Do not edit.
 */
package ie.ucd.bon.ast;

import java.util.List;
import ie.ucd.bon.source.SourceLocation;
import ie.ucd.bon.util.StringUtil;

public class ClientRelation extends StaticRelation {


  public final StaticRef client;
  public final StaticRef supplier;
  public final ClientEntityExpression clientEntities;
  public final TypeMark typeMark;

  public final String semanticLabel;


  // === Constructors and Factories ===
  protected ClientRelation(StaticRef client, StaticRef supplier, ClientEntityExpression clientEntities, TypeMark typeMark, String semanticLabel, SourceLocation location) {
    super(location);
    this.client = client; assert client != null;
    this.supplier = supplier; assert supplier != null;
    this.clientEntities = clientEntities; 
    this.typeMark = typeMark; 
    this.semanticLabel = semanticLabel; 
  }

  public static ClientRelation mk(StaticRef client, StaticRef supplier, ClientEntityExpression clientEntities, TypeMark typeMark, String semanticLabel, SourceLocation location) {
    return new ClientRelation(client, supplier, clientEntities, typeMark, semanticLabel, location);
  }

  // === Accessors ===

  public StaticRef getClient() { return client; }
  public StaticRef getSupplier() { return supplier; }
  public ClientEntityExpression getClientEntities() { return clientEntities; }
  public TypeMark getTypeMark() { return typeMark; }
  public String getSemanticLabel() { return semanticLabel; }

  // === Visitor ===
  public void accept(IVisitorWithAdditions visitor) {
    visitor.visitClientRelation(this, client, supplier, clientEntities, typeMark, semanticLabel, getLocation());
  }

  // === Others ===
  @Override
  public ClientRelation clone() {
    StaticRef newClient = client == null ? null : client.clone();
    StaticRef newSupplier = supplier == null ? null : supplier.clone();
    ClientEntityExpression newClientEntities = clientEntities == null ? null : clientEntities.clone();
    TypeMark newTypeMark = typeMark == null ? null : typeMark.clone();
    String newSemanticLabel = semanticLabel;
    return ClientRelation.mk(newClient, newSupplier, newClientEntities, newTypeMark, newSemanticLabel, getLocation());
  }

  @Override
  public String toString() {
    return StringUtil.prettyPrint(this);
  }
}

