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
      DynamicContext childCtx = new DynamicContext(context);
      if( contents.apply(childCtx) ){
        String sql = childCtx.getSql();
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
