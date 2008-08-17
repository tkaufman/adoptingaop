using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AdoptingAOP
{
    public interface IAccountDao
    {
        void Create(Account act);

        Account Read(string number);

        void Update(Account act);

        void Delete(Account act);
    }
}
