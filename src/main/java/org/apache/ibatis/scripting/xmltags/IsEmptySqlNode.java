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

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * @author jun.zhao
 */
public class IsEmptySqlNode implements SqlNode {
  private final ExpressionEvaluator evaluator;
  protected final String prepend;
  protected final String property;
  protected final SqlNode contents;

  public IsEmptySqlNode(SqlNode contents, String prepend, String property) {
    this.prepend = prepend;
    this.property = property;
    this.contents = contents;
    this.evaluator = new ExpressionEvaluator();
  }

  @Override
  public boolean apply(DynamicContext context) {
    Object value = evaluator.evaluate(property, context.getBindings());
    if (isEmpty(value)) {
      context.appendSql(prepend);
      contents.apply(context);
      return true;
    }
    return false;
  }

  protected boolean isEmpty(Object value){
    if( value == null ){
      return true;
    }

    if (value instanceof Collection) {
      return ((Collection) value).isEmpty();
    } else if (value.getClass().isArray()) {
      return Array.getLength(value) == 0;
    } else {
      return String.valueOf(value).equals("");
    }
  }

}
