using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Castle.Core.Resource;
using Castle.Windsor.Configuration.Interpreters;
using Castle.Windsor;

namespace AdoptingAOP
{
    [TestClass]
    public class AccountServiceTest
    {
        public AccountServiceTest()
        {
        }

        private TestContext testContextInstance;
        private IAccountService service;

        public TestContext TestContext
        {
            get
            {
                return testContextInstance;
            }
            set
            {
                testContextInstance = value;
            }
        }

        #region Additional test attributes
        //
        // You can use the following additional attributes as you write your tests:
        //
        // Use ClassInitialize to run code before running the first test in the class
        // [ClassInitialize()]
        // public static void MyClassInitialize(TestContext testContext) { }
        //
        // Use ClassCleanup to run code after all tests in a class have run
        // [ClassCleanup()]
        // public static void MyClassCleanup() { }
        //
        // Use TestInitialize to run code before running each test 
        // [TestInitialize()]
        // public void MyTestInitialize() { }
        //
        // Use TestCleanup to run code after each test has run
        // [TestCleanup()]
        // public void MyTestCleanup() { }
        //
        #endregion

        [TestInitialize()]
        public void SetupWindsor() {
            IWindsorContainer container =
                new WindsorContainer(
                    new XmlInterpreter(new ConfigResource("castle")));
            service = container.GetService<IAccountService>();
        }
        
        [TestMethod]
        public void TestFindNullByNumber()
        {
            Account act = service.FindByNumber("1234123412341234");
            Assert.IsNull(act);
        }

        [TestMethod]
        public void TestFindByNumber()
        {
            Account act = BuildAccount();
            service.Create(act);

            Account foundAct = service.FindByNumber(act.Number);
            Assert.IsNotNull(foundAct);
            Assert.AreEqual(act.Number, foundAct.Number);
            Assert.AreEqual(act.Holder, foundAct.Holder);
            Assert.AreEqual(act.Balance, foundAct.Balance);
        }

        private Account BuildAccount()
        {
            Account act = new Account();
            act.Id = 1;
            act.Number = "1234123412341234";
            act.Holder = "The Todd";
            act.Balance = new Decimal(10000000);
            return act;
        }
    }
}
