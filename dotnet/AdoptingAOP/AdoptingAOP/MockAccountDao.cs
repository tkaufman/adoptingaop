using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AdoptingAOP
{
    public class MockAccountDao : IAccountDao
    {
        #region IAccountDao Members

        private IList<Account> db = new List<Account>();

        public void Create(Account act)
        {
            db.Add(act);
        }

        public Account Read(string number)
        {
            foreach (Account act in db)
            {
                if (act.Number.Equals(number))
                {
                    return act;
                }
            }
            return null;
        }

        public void Update(Account act)
        {
            Account foundAct = Read(act.Number);
            if (foundAct != null)
            {
                foundAct.Holder = act.Holder;
                foundAct.Balance = act.Balance;
            }
            else
            {
                db.Add(act);
            }
        }

        public void Delete(Account act)
        {
            Account foundAct = Read(act.Number);
            if (foundAct != null)
            {
                db.Remove(foundAct);
            }
        }

        #endregion
    }
}
