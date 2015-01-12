/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.routingslip;

import org.springframework.messaging.Message;

/**
 * The {@code RoutingSlip} strategy to determine the next {@code replyChannel}.
 * <p>
 * This strategy is called repeatedly until null or an empty String is returned.
 *
 * @author Artem Bilan
 * @since 4.1
 * @see org.springframework.integration.handler.AbstractMessageProducingHandler
 */
public interface RoutingSlipRouteStrategy {

	Object getNextPath(Message<?> requestMessage, Object reply);

}