using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Castle.Core.Interceptor;
using System.Threading;

namespace AdoptingAOP
{
    class SecureInterceptor : IInterceptor
    {
        public void Intercept(IInvocation invocation)
        {
            if (true)
            {
                throw new ApplicationException("Not authorized!!!!");
            }
            Console.WriteLine("Entering method " + invocation.Method.Name + " with user " + Thread.CurrentPrincipal.Identity.Name);
            invocation.Proceed();
            Console.WriteLine("Exiting method " + invocation.Method.Name + " with user " + Thread.CurrentPrincipal.Identity.Name);
        }

    }
}
