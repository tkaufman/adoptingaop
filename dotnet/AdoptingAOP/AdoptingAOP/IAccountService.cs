using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AdoptingAOP
{
    public interface IAccountService
    {
        Account FindByNumber(String number);
        void Create(Account act);
    }
}
