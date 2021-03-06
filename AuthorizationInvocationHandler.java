import java.io.Serializable;
import java.lang.reflect.InvocationHandler; 
import java.lang.reflect.Method;

public class AuthorizationInvocationHandler implements InvocationHandler, Serializable { 
	private Object objectImpl;
	
	public AuthorizationInvocationHandler(Object impl) {
		this.objectImpl = impl;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable, AuthorizationException{ 
		if (method.isAnnotationPresent(RequiresRole.class)) 
		{
			RequiresRole test = method.getAnnotation(RequiresRole.class);
			String session = (String) args[0];
			if (session.equals(test.value())) 
			{ 
				return method.invoke(objectImpl, args); 
			} 
			else 
			{ 
				throw new AuthorizationException(method.getName());
			} 
		} 
		else 
		{ 
			return method.invoke(objectImpl, args);
		}  
	}
}