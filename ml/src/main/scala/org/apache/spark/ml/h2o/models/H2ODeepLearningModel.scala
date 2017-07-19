/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.apache.spark.ml.h2o.models

import hex.deeplearning.DeepLearningModel
import org.apache.spark.annotation.Since
import org.apache.spark.h2o.H2OContext
import org.apache.spark.ml.util.{Identifiable, MLReadable, MLReader, MLWritable}
import org.apache.spark.sql.SQLContext

class H2ODeepLearningModel(model: DeepLearningModel,
                           override val uid: String)(h2oContext: H2OContext, sqlContext: SQLContext)
  extends H2OModel[H2ODeepLearningModel, DeepLearningModel](model, h2oContext, sqlContext) {

  def this(model: DeepLearningModel)
          (implicit h2oContext: H2OContext, sqlContext: SQLContext) = this(model, Identifiable.randomUID("dlModel"))(h2oContext, sqlContext)

  override def defaultFileName: String = H2ODeepLearningModel.defaultFileName
}

object H2ODeepLearningModel extends MLReadable[H2ODeepLearningModel] {

  val defaultFileName = "dl_model"

  @Since("1.6.0")
  override def read: MLReader[H2ODeepLearningModel] = new H2OModelReader[H2ODeepLearningModel, DeepLearningModel](defaultFileName) {
    override protected def make(model: DeepLearningModel, uid: String)
                               (implicit h2oContext: H2OContext, sqLContext: SQLContext): H2ODeepLearningModel =
      new H2ODeepLearningModel(model, uid)(h2oContext, sqlContext)
  }

  @Since("1.6.0")
  override def load(path: String): H2ODeepLearningModel = super.load(path)
}
