require 'rexml/document'
require 'account.rb'

class AccountDao

    def initialize
        file = File.new( "accounts.xml" )
        @doc = REXML::Document.new file
    end

    def lookup(id)
        results = Array.new
        @doc.root.each_element_with_attribute('id', id) { |e| bind_to_account(results, e) }
        if results.size == 1
            results[0]
        else
            nil
        end
    end

    def bind_to_account(arr, el)
        account = Account.new
        account.id = el.attributes["id"]
        account.number = el.elements["number"].text
        account.balance = el.elements["balance"].text
        account.holder = el.elements["holder"].text
        arr << account
    end

end