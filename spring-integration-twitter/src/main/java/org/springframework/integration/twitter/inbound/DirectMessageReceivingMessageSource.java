/*
 * Copyright 2002-2010 the original author or authors.
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
package org.springframework.integration.twitter.inbound;

import java.util.Comparator;
import java.util.List;

import org.springframework.integration.MessagingException;
import org.springframework.integration.twitter.core.Tweet;
import org.springframework.integration.twitter.core.TwitterOperations;
import org.springframework.util.CollectionUtils;

import twitter4j.Paging;

/**
 * This class handles support for receiving DMs (direct messages) using Twitter.
 *
 * @author Josh Long
 * @author Oleg Zhurakousky
 * @since 2.0
 */
public class DirectMessageReceivingMessageSource extends AbstractTwitterMessageSource<Tweet> {
	
	public DirectMessageReceivingMessageSource(TwitterOperations twitter){
		super(twitter);
	}
	
	@Override
	public String getComponentType() {
		return "twitter:inbound-dm-channel-adapter";  
	}

	@Override
	Runnable getApiCallback() {
		Runnable apiCallback = new Runnable() {	
			public void run() {
				try {
					long sinceId = getMarkerId();
					if (tweets.size() <= prefetchThreshold){
						List<Tweet> dms = !hasMarkedStatus() 
							? twitter.getDirectMessages() 
							: twitter.getDirectMessages(new Paging(sinceId));
			
							if (!CollectionUtils.isEmpty(dms)){
								forwardAll(dms);
							}	
					} 
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof RuntimeException){
						throw (RuntimeException)e;
					}
					else {
						throw new MessagingException("Failed to poll for Twitter mentions updates", e);
					}
				}
			}
		};
		return apiCallback;
	}

	@SuppressWarnings("rawtypes")
	protected Comparator getComparator() {
		return new Comparator<Tweet>() {
			public int compare(Tweet tweet1, Tweet tweet2) {
				return tweet1.getCreatedAt().compareTo(tweet2.getCreatedAt());
			}
		};
	}
}
