using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AdoptingAOP
{
    public class Account
    {
        private Int64 id;
        private String number;
        private String holder;
        private Decimal balance;

        public Account()
        {
        }

        public virtual Int64 Id
        {
            get
            {
                return this.id;
            }
            set
            {
                this.id = value;
            }
        }

        public virtual String Number
        {
            get
            {
                return this.number;
            }
            set
            {
                this.number = value;
            }
        }
        public virtual String Holder
        {
            get
            {
                return this.holder;
            }
            set
            {
                this.holder = value;
            }
        }
        public virtual Decimal Balance
        {
            get
            {
                return this.balance;
            }
            set
            {
                this.balance = value;
            }
        }
    }
}

