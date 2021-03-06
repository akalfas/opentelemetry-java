/*
 * Copyright 2019, OpenTelemetry Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opentelemetry.sdk.trace;

import io.opentelemetry.trace.SpanId;
import io.opentelemetry.trace.TraceId;
import java.util.Random;

final class RandomIdsGenerator implements IdsGenerator {

  private static final ThreadLocal<Random> threadLocalRandom =
      new ThreadLocal<Random>() {
        @Override
        protected Random initialValue() {
          return new Random();
        }
      };
  private static final long INVALID_ID = 0;

  @Override
  public SpanId generateSpanId() {
    long id;
    do {
      id = threadLocalRandom.get().nextLong();
    } while (id == INVALID_ID);
    return new SpanId(id);
  }

  @Override
  public TraceId generateTraceId() {
    long idHi;
    long idLo;
    do {
      Random random = threadLocalRandom.get();
      idHi = random.nextLong();
      idLo = random.nextLong();
    } while (idHi == INVALID_ID && idLo == INVALID_ID);
    return new TraceId(idHi, idLo);
  }
}
