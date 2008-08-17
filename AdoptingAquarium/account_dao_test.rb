require "test/unit"
require "account_dao"
require 'account_advice'

class AccountDaoTest < Test::Unit::TestCase

  def setup
      @dao = AccountDao.new
  end

  def test_lookup
      act = @dao.lookup("1")
      assert_not_nil(act)
      assert_instance_of(Account, act)
      assert_equal("1", act.id)
      assert_equal("1234123412341234", act.number)
      assert_equal("The Todd", act.holder)
      assert_equal("10000.00", act.balance)
  end
end