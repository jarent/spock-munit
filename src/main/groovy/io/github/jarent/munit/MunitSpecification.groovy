package io.github.jarent.munit

import org.mule.api.MuleContext
import org.mule.api.MuleEvent
import org.mule.api.MuleException
import org.mule.api.MuleMessage
import org.mule.munit.common.mocking.MessageProcessorMocker
import org.mule.munit.common.mocking.MunitSpy
import org.mule.munit.common.mocking.MunitVerifier
import org.mule.munit.runner.functional.FunctionalMunitSuite;

import spock.lang.*


abstract class MunitSpecification extends Specification {
	
	protected static MuleContext muleContext;
	
	@Shared
	private FunctionalMunitSuite munitHelper    
	
	// run before the first feature method
	def setupSpec() {
		
		munitHelper = new FunctionalMunitSuite() {
			
			@Override
			protected String getConfigResources() {
				
					return MunitSpecification.this.getConfigResources()
			}
			
			@Override
			protected boolean haveToDisableInboundEndpoints() {
				return MunitSpecification.this.haveToDisableInboundEndpoints()
			}
		
			@Override
			protected boolean haveToMockMuleConnectors() {
				return MunitSpecification.this.haveToMockMuleConnectors()
			}
		}
		
		this.muleContext = munitHelper.muleContext;		
	}    
	
	protected final MuleEvent runFlow(String name, MuleEvent event)
	throws MuleException
	{
		return munitHelper.runFlow(name, event)
	}
	
	
	protected String getConfigResources() {
		return munitHelper.getConfigResources() 
	}
	
	//dependency to default value from FunctionalMunitSuite
	protected boolean haveToDisableInboundEndpoints() {
		return true ;
	}
	
	//dependency to default value from FunctionalMunitSuite
	protected boolean haveToMockMuleConnectors() {
		return true;
	}
	
	protected final MuleEvent testEvent(Object payload)
	throws Exception
	{
		return munitHelper.testEvent(payload);
	}
	
	protected final MuleMessage muleMessageWithPayload(Object payload)
	{
		return munitHelper.muleMessageWithPayload(payload);
	}
	
	protected final MessageProcessorMocker whenMessageProcessor(String name)
	{
		return munitHelper.whenMessageProcessor(name);
	}
		
	protected final MunitVerifier verifyCallOfMessageProcessor(String name)
	{
		return munitHelper.verifyCallOfMessageProcessor(name);
	}

	protected final MunitSpy spyMessageProcessor(String name)
	{
		return munitHelper.spyMessageProcessor(name);
	}
	
	//run before every feature method
	def setup() {
		munitHelper.__setUpMunit()
	}
	
	//run after last feature method
	def cleanupSpec() {
		munitHelper.killMule()
	}
	
	
	
	// run after every feature method
	def cleanup() {

		munitHelper.__restartMunit()	
	}

}
