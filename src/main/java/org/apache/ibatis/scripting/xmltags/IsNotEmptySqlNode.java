/**
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.scripting.xmltags;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.apache.ibatis.session.Configuration;

import java.util.Map;

/**
 * @author jun.zhao
 */
public class IsNotEmptySqlNode extends IsEmptySqlNode {
  private final ExpressionEvaluator evaluator;

  public IsNotEmptySqlNode(SqlNode contents, String prepend, String property) {
    super(contents, prepend, property);
    this.evaluator = new ExpressionEvaluator();
  }

  @Override
  public boolean apply(DynamicContext context) {
    Object value = evaluator.evaluate(property, context.getBindings());
    if (!isEmpty(value)) {
      DynamicContext sharedBindingCtx = new SharedBindingDynamicContext(context);
      if( contents.apply(sharedBindingCtx) ){
        String sql = sharedBindingCtx.getSql();
        if( !"".equals(sql) ){
          context.appendSql(prepend);
          context.appendSql(sql);
        }
      }
      return true;
    }
    return false;
  }



}
