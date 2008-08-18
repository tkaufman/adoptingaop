require 'aquarium'
require 'account_dao'

class AccountAdvice

    Aquarium::Aspects::Aspect.new :around, :calls_to => :bind_to_account, :on_types => [AccountDao] do |join_point, object, *args|
            p "Entering: #{join_point.target_type.name}##{join_point.method_name} for object #{object} at #{Time.now}"
            result = join_point.proceed
            p "Leaving: #{join_point.target_type.name}##{join_point.method_name} for object #{object} at #{Time.now}"
            result  # block needs to return the result of the "proceed"!
    end

end