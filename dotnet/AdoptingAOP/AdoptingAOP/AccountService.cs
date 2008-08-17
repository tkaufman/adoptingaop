using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AdoptingAOP
{
    class AccountService : IAccountService
    {
        IAccountDao dao;

        public virtual IAccountDao Dao
        {
            get
            {
                return this.dao;
            }
            set
            {
                this.dao = value;
            }
        }

        #region IAccountService Members

        public Account FindByNumber(string number)
        {
            return dao.Read(number);
        }

        public void Create(Account act)
        {
            dao.Create(act);
        }

        #endregion
    }
}
