package main

import (
	"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
	"errors"
	"strconv"
	"bytes"
)



type SimpleChaincode struct {
}
//记录当前链码上有多少个记录
var RNO = 0
var ChainCodeVersion = "v0.2"

type Record struct {
	Key       string
	Value       string
}


/**
	常见工具方法，暂不拆分
 */

 /**
 	参数基本判断
  */
func sanitize_arguments(strs []string) error{
	for i, val:= range strs {
		if len(val) <= 0 {
			return errors.New("Argument " + strconv.Itoa(i) + " must be a non-empty string")
		}
		if len(val) > 1024 {
			return errors.New("Argument " + strconv.Itoa(i) + " must be <= 1024 characters")
		}
	}
	return nil
}



/*
  智能合约初始化调用,一个链码版本只运行一次

*/
func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("Init ChainCode...............",ChainCodeVersion)
	return shim.Success(nil)
}

/*
	智能合约发起请求时调用 {"Args":["invoke","a","b","10"]}
	func (stub *ChaincodeStub) GetFunctionAndParameters() (function string, params []string)
  返回参数 function 标记调用方法的名称"invoke"
	返回参数 params   标记调用方法的参数数组["a","b","10"]
*/

func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	// 获取请求调用智能合约的方法和参数
	function, args := stub.GetFunctionAndParameters()
	fmt.Println("recive params....",function,"          ",args)
	// Route to the appropriate handler function to interact with the ledger appropriately
	if function == "addAsset" {
		fmt.Println("run function addcard",function,"          ",args)
		return t.addAsset(stub, args)
	} else if function == "updatekv" {
		fmt.Println("run function updatecard",function,"          ",args)
		return t.updatekv(stub, args)
	} else if function == "delkv" {
		fmt.Println("run function deletecard",function,"          ",args)
		return t.delkv(stub, args)
	} else if function == "query" {
		fmt.Println("run function query",function,"          ",args)
		return t.query(stub, args)
	} else if function == "queryhistory" {
		fmt.Println("run function queryhistory",function,"          ",args)
		return t.queryhistory(stub, args)
	} else if function == "queryAssetsByOwner" {
		fmt.Println("run function queryAssetsByOwner",function,"          ",args)
		return t.queryAssetsByOwner(stub, args)
	}
	return shim.Success(nil)
}

/*
  	向链码中添加一个KV结构
	该版本测试中VALUE由CLI进行传入，以换取链码的更大自由性
*/
func (t *SimpleChaincode) addAsset(stub shim.ChaincodeStubInterface, args []string) pb.Response {

	//对传入的参数进行检查
	if len(args) != 2 {

		return shim.Error("Incorrect number of arguments. Expecting 2. key of the variable and value to set")
	}

	err := sanitize_arguments(args)
	if err != nil {

		return shim.Error(err.Error())
	}

	//检查资产Id是否重复
	assetAsByte, err := stub.GetState(args[0])
	if err != nil {
		return shim.Error(err.Error())
	}

	if assetAsByte != nil {
		return shim.Error("Error,The asset ID has already existed!")
	}

	//向账本中添加数据
	//var pertmp Record
	//pertmp = Record{Key: args[0], Value: args[1]}

	//fmt.Println("Record is ",pertmp)
	//jsonrecode,_ := json.Marshal(&pertmp)
	err = stub.PutState(args[0], []byte(args[1]))
	//fmt.Println("put key is ", pertmp.Key)
	if err != nil {
		return shim.Error("Error retrieving asset")
	}
	//在控制台进行输出添加结果
	return shim.Success([]byte(fmt.Sprintf("Success endorse a asset,k:%s,v:%s. \n",args[0],args[1])))
}

/**
	查询某个人的所有资产
	测试！
 */
 func (t *SimpleChaincode) queryAssetsByOwner(stub shim.ChaincodeStubInterface, args []string) pb.Response {

	 //对传入的参数进行检查
 	if len(args) != 1 {

		 return shim.Error("Incorrect number of arguments. Expecting 1.")
	 }

	 //查询
	 assetOwner := args[0]
	 queryStr := fmt.Sprintf("{\"selector\":{\"assetOwner\":\"%s\"}}",assetOwner)

	 resultIterator, err := stub.GetQueryResult(queryStr)

	 if err != nil {
	 	return shim.Error(err.Error())
	 }

	 defer resultIterator.Close()

	 var buffer bytes.Buffer
	 buffer.WriteString("[")

	 isWrite := false

	 for resultIterator.HasNext() {
		 queryResponse , err := resultIterator.Next()
		 if err != nil {
			 return shim.Error(err.Error())
		 }

		 if isWrite == true {
			 buffer.WriteString(",")
		 }
		 //以JSON的形式进行拼接，返回
		 buffer.WriteString("{\"key\":")
		 buffer.WriteString("\""+queryResponse.Key+"\"")//fixed:将key的取值用引号处理,已符合JSON要求
		 buffer.WriteString(",\"record\": ")
		 buffer.WriteString(string(queryResponse.Value))
		 buffer.WriteString("}")
		 isWrite = true
	 }
	 buffer.WriteString("]")

	 return shim.Success(buffer.Bytes())
 }




/*
	向链码中已经存在的记录做一次更新操作
*/
func (t *SimpleChaincode) updatekv(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	t.addAsset(stub,args)
	return shim.Success(nil)
}

/*
	删除链码中的一个记录,调用DelState函数操作
*/
func (t *SimpleChaincode) delkv(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	fmt.Println("删除账本中的一条数据     Keys:",args[0])
	deletekey:=args[0]
	err:=stub.DelState(deletekey)
	if err != nil {
		return shim.Error("Error retrieving data")
	}
	fmt.Println("删除成功")
	return shim.Success(nil)
}

/*
	查询链码中的数据
*/
func (t *SimpleChaincode) query(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	fmt.Println("查询账本中的数据 Keys:",args[0])
	queryparams:=args[0]
	redBytes, err := stub.GetState(queryparams)
	if err != nil {
		shim.Error("Error retrieving data")
	}
	fmt.Println("query resoult"+string(redBytes))
	return shim.Success(redBytes)
}
/*
	根据给定的键key，查询该键的所有历史记录

*/
func (t *SimpleChaincode) queryhistory(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var records []Record
	fmt.Println("query key histroy......................",args[0])
	historyIer, err := stub.GetHistoryForKey(args[0])
	if err != nil {
		fmt.Println(err)
		return shim.Error("query error")
	}
	for historyIer.HasNext() {
		var tmp Record=Record{}
		modification, err := historyIer.Next()
		if err != nil {
			fmt.Println(err)
			return shim.Error("query error")
		}
		fmt.Println("Returning information about", string(modification.Value))
		err=json.Unmarshal(modification.Value,&tmp)
		records=append(records,tmp)
	}
	jsonrecode,_ := json.Marshal(&records)
	return shim.Success(jsonrecode)
}



func main() {
	fmt.Println("Fabric Epoint Common Chain Code ..........")
	err := shim.Start(new(SimpleChaincode))
	if err != nil {
		fmt.Printf("Error starting Simple chaincode: %s", err)
	}
}

