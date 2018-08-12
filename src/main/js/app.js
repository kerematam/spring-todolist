'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

const Button = require('react-bootstrap/lib/Button');
const FormGroup = require('react-bootstrap/lib/FormGroup');
const FormControl = require('react-bootstrap/lib/FormControl');
const ControlLabel = require('react-bootstrap/lib/ControlLabel');
//import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";

class App extends React.Component {
	
	constructor(props) {
		super(props);
		this.state = {userId :'', todolists : [], isLogin : false};

	}
	
	
	componentDidMount() {		
		
		var reqURL = '/isLogin';
		
		var self = this;
		fetch(reqURL, {
		    method: 'GET',
		    headers: {
		    	'Content-Type': 'application/json'
	        }   
		}).then(function (response) {
				
			  response.json().then(function(userId) {
				  	if(userId > 0) {
				  		self.setState({userId : userId});
				  		var reqURL = '/users/' + userId + '/todolists';		
				  		
						client({method: 'GET', path: reqURL}).done(response => {
							self.setState({isLogin: true});
							self.setState({todolists: response.entity});

						});
				  	}			      
			  });
			  
		})
	}

	//	<TodoList todolist={this.state.todolist}/>
	
	render() {
		console.log('state : ', this.state.todolists);
		
		if(this.state.isLogin)
			return (
			<div>
				<UserRegister />
				<TodoLists todolists={this.state.todolists} userId={this.state.userId}/>
			</div>
			);
		else
			return (
				<div>
					<UserRegister />
				</div>);
	}
}


class TodoLists extends React.Component{
	
	renderNewTodoListForm(todoList) {
		console.log("todolists : ", this.props.todolists);
	    return <NewTodoListForm userId={this.props.userId} />; 
	}
	
	render() {		
		var todolists = this.props.todolists.map(todolist =>
		<TodoList key={todolist.id} todolist={todolist}/>
		);
		
		return (
			<div>
				<hr className="half-rule"/>
				<div className="row">
					{this.renderNewTodoListForm()}
				</div> 
				<div className="row">
					{todolists}
				</div>
			</div>
		)
	}
}


class NewTodoListForm extends React.Component {
	constructor(props) {
		super(props);
	  
	    this.state = {
	      listName : '',
	    };
	   	    	
	    this.handleChange = this.handleChange.bind(this);
	    this.handleClick = this.handleClick.bind(this);
	}
	
	handleChange(e) {
	    this.setState({
	        [e.target.id]: e.target.value
	      });
	}
	
	handleClick(e) {
		e.preventDefault();

		var newTodoList = {};
		newTodoList['name'] = this.state.listName;
		console.log("newTodoList : ", newTodoList);
		
		
		var reqURL = '/users/' + this.props.userId + '/todolists'; 
		console.log("reqURL : ", reqURL);
		fetch(reqURL, {
		    method: 'POST',
		    body: JSON.stringify(newTodoList),
		    headers: {
		    	'Content-Type': 'application/json'
	        }   
		}).then(function (response) {
		   // refresh page
		   location.reload();
		})
	}
	
	render(){
		return(
		  <div className="row">
			<form>
			  List Name:<br/>
			  <input id='listName' type='text' value={this.state.listName}  onChange={this.handleChange} />
			  <br/>
			  <button
			  className="btn"
			  onClick={this.handleClick} value='Create List' >Create List</button>
			</form>
		  </div>)
	};
	
}



class UserRegister extends React.Component {
	
	constructor(props) {
		super(props);
		
	    this.handleChange = this.handleChange.bind(this);
	    this.handleLogin = this.handleLogin.bind(this);
	    this.handleRegister = this.handleRegister.bind(this);


	    this.checkLogin();
	    
	    
		this.state = {
	      username: '',
	      password: '',
	      isLogin : false
		};
	}
	
	checkLogin() {
		
		var reqURL = '/isLogin';
		var self = this;
		fetch(reqURL, {
		    method: 'GET',
		    headers: {
		    	'Content-Type': 'application/json'
	        }   
		}).then(function (response) {
			  response.json().then(function(data) {
			      
				  console.log("userid",data);
			      
			      if(data > 0)
			    	  self.setState({isLogin : true})
			      else 
			    	  self.setState({isLogin : false})
			    	  
			    });
		})
	} 
	
	
	handleChange(e) {
	    this.setState({
	        [e.target.id]: e.target.value
	    });
	}
	
	handleRegister() {
		console.log('this is register');
		console.log('register user :', this.state.username);
		console.log('register user :', this.state.password);
		
		
		var registerUser = {};
		registerUser['username'] = this.state.username;
		registerUser['password'] = this.state.password;
		
		var reqURL = '/users';
		fetch(reqURL, {
		    method: 'POST',
		    headers: {
		    	'Content-Type': 'application/json',
	        },
			body: JSON.stringify(registerUser),
		}).then(function (response) {
			console.log(response);
			location.reload();
		})
		
	}
	
	handleLogout(){
		
		var reqURL = 'logout';
		fetch(reqURL, {
		    method: 'GET',
		    headers: {
		    	'Content-Type': 'application/json'
	        }   
		}).then(function (response) {
			console.log(response);
		   // refresh page
		   location.reload();
		})
	}
	

	
	handleLogin(e) {
		e.preventDefault();
		
		console.log('this is submit');
		console.log(this.state.username);
		console.log(this.state.password);
		console.log(this.props.id);
		
		var authUser = {};
		authUser['username'] = this.state.username;
		authUser['password'] = this.state.password;
	
		var reqURL = '/login';
		
		fetch(reqURL, {
		    method: 'POST',
		    body: JSON.stringify(authUser),
		    headers: {
		    	'Content-Type': 'application/json'
	        }   
		}).then(function (response) {
		   // refresh page
		   location.reload();
		})
	}
	
	validateForm() {
		return this.state.username.length > 0 && this.state.password.length > 0;
	}
// 		            type="submit"
	render() {
		//var userLogin = true;
		
		if (!this.state.isLogin) 
		    return (    
		      <div className="Login"   className="centered" >
		        <form >
		          <FormGroup controlId="username" bsSize="large">
		            <ControlLabel required >Username : </ControlLabel>
		            <FormControl
		              autoFocus
		              type="username"
		              value={this.state.username}
		              onChange={this.handleChange}
		            />
		          </FormGroup>
		          <FormGroup controlId="password" bsSize="large">
		            <ControlLabel>Password :</ControlLabel>
		            <FormControl
		              value={this.state.password}
		              onChange={this.handleChange}
		              type="password"
		            />
		          </FormGroup>
		          <Button
		            name="login"
		          	block
		            bsSize="large"
		            disabled={!this.validateForm()}
		          	onClick={this.handleLogin}

		          >
		            Login
		          </Button>
			      <Button
			      		name="register"
			            block
			            bsSize="large"
			            disabled={!this.validateForm()}
			      		onClick={this.handleRegister}
			          >
			            Register
			       </Button>		            
		        </form>
		      </div>
		    );
		else
			return (    
				      <div className="Login"   className="centered" >
				        <form onSubmit={this.handleSubmit}>
					       <Button
						      		id="logout"
						            block
						            
						            onClick={this.handleLogout}
						          >
						          Logout
						    </Button>
				        </form>
				      </div>
				    );
	  }


	
}





class DeleteItemButton extends React.Component {
	
	constructor(props) {
		super(props);
	    this.handleClick = this.handleClick.bind(this);
	}
	
	handleClick(e){
		console.log(this.props.ItemId);
		
		var reqURL = '/users/1/todolists/1/items/' + this.props.ItemId;
		console.log(reqURL);
		
		fetch(reqURL, {
		    method: 'DELETE',
		    headers: {
		    	'Content-Type': 'application/json'
	        }   
		}).then(function (response) {
			location.reload();


		})
		
		
	}
	
	
	render() {
		return(
			<button onClick={this.handleClick}>Delete</button>
        );
	}
	
	
}

class NewItemForm extends React.Component {
	
	constructor(props) {
		super(props);
	  
	    this.state = {
	      id : '',
	      name : '',
	      epochTime : ''
	    };
	   	    	
	    this.handleChange = this.handleChange.bind(this);
	    this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleChange(e) {

	    this.setState({
	        [e.target.id]: e.target.value
	      });
	}
	  
	handleSubmit(e) {
		e.preventDefault();
		console.log(this.state.id);
		console.log(this.state.name);
		
		
		var newItem = {};
		//newItem['id'] = this.state.id;
		newItem['name'] = this.state.name;
		var epoch_duedate = new Date(this.state.epochTime).getTime();
		newItem['epochTime'] = epoch_duedate;
		console.log(newItem['epochTime']);
		var reqURL = '/users/1/todolists/' + this.props.TodoListId + '/items';
		
		fetch(reqURL, {
		    method: 'POST',
		    body: JSON.stringify(newItem),
		    headers: {
		    	'Content-Type': 'application/json'
	        }   
		}).then(function (response) {
		   // refresh page
		   location.reload();
		})
	}


	render(){
		return(
		<div>
			<form onSubmit={this.handleSubmit}>
			  Name:<br/>
			  <input id='name' type='text' value={this.state.text}  onChange={this.handleChange} />
			  <br/>Due Date :<br/>
			  <input id='epochTime' type='date' value={this.state.date}  onChange={this.handleChange} />
			  <br/>
			  <br/>
			  <input type='submit' value='Add Item' />
			</form>
		  </div>)
	};
}


class ItemStatusCheckbox extends React.Component{
	// https://stackoverflow.com/questions/43602665/uncheck-checkbox-programatically-in-reactjs
	constructor(props) {
		super(props);
	  
	//	console.log();
		
	    this.state = {
	    	      isDone: this.props.item.isDone,
	    	    };

	   	    	
	    //this.handleChange = this.handleChange.bind(this);
	    this.handleInputChange = this.handleInputChange.bind(this);
	}
	
	
	  handleInputChange(event) {
		  console.log("değisti");
		    const target = event.target;
		    const value = target.type === 'checkbox' ? target.checked : target.value;
		    const name = target.name;

		    this.setState({
		      [name]: value
		    });
		    
		    
		    var updateItem = this.props.item;
		    updateItem['isDone'] = value;
		    var reqURL = '/users/' + updateItem.todoList.user.id + '/todolists/' + updateItem.todoList.id + '/items/' + updateItem['id'];

		    console.log(reqURL);
		
		    var self = this;
			fetch(reqURL, {
			    method: 'PUT',
			    body: JSON.stringify(updateItem),
			    headers: {
			    	'Content-Type': 'application/json'
		        }   
			}).then(function (response) {
				  response.json().then(function(data) {
					  	console.log(data); 
					  	
					  	if(data==false)
					  	{
					  	    self.setState({isDone: false});
					  	    alert("Item is depended to uncomplete item.");
					  	}
					  	
					  	
				  });
			})
		    
		  }
		
	render(){
		return(
				
			    <input
	            name="isDone"
	            type="checkbox"
	            checked={this.state.isDone}
	            onChange={this.handleInputChange} />);
	};
	
}

class TodoList extends React.Component{
	
	constructor(props) {
		super(props);
		this.state = {todolist: []};
		
		this.handleDeleteList = this.handleDeleteList.bind(this);

	}
	
	componentDidMount() { 
		// todolist
		var reqURL =  '/users/1/todolists/' + this.props.todolist.id + '/items' ; 
		client({method: 'GET', path: reqURL }).done(response => {
			this.setState({todolist: response.entity});
		});
		
		
	}
	
	handleDeleteList(){		
		
		var reqURL = '/users/' + this.props.todolist.user.id + '/todolists/' + this.props.todolist.id;
		console.log(reqURL);
		
		fetch(reqURL, {
		    method: 'DELETE',
		    headers: {
		    	'Content-Type': 'application/json'
	        }   
		}).then(function (response) {
			console.log('response status : ', response.status);
			if(response.status == 500)
				alert("List with Items can not be deleted. Please delete list items before delete.");
			else if (response.status == 200) 
				location.reload();
			else 
				alert('Request coould not be processed');

		})
		
		
		//curl -X DELETE http://localhost:8080/users/1/todolists/1
		
	}

	renderNewItemForm(todoListId) {
	    return <NewItemForm TodoListId={todoListId} />;
	};
	
	render() {	
				
		var todolist = this.state.todolist.map(item =>
		<Item key={item.id} item={item} todolist={this.state.todolist} />
		);
		
		return (
			<div>
			<hr className="half-rule"></hr>
			<div  className="col-sm-12">
				<div className="form-inline" >
					<h3 className="form-group mb-2 " >{this.props.todolist.name}</h3>
					<button className="btn" onClick={this.handleDeleteList} > Delete List </button>
				</div>
				<div>
					<table className="table-bordered">
						<tbody>
							<tr>
								<th>ID</th>
								<th>Name</th>
								<th>Due Date</th>
								<th>Action</th>
								<th>Complete</th>
								<th>Dep Items</th>
								<th>Add Dep Item</th>
							</tr>
							{todolist}
						</tbody>		
					</table>
				</div>
				
				<div>
					{this.renderNewItemForm(this.props.todolist.id)}
				</div>
			</div>
			</div>
		)
	}
	

}


class AddDependency extends React.Component {

	constructor(props) {
		super(props);
		this.state = {seletctItem: ""};
		this.handleChange = this.handleChange.bind(this);
		this.handleClick = this.handleClick.bind(this);
	}
	
	handleChange(e) {
		console.log(e.target.value);
	    this.setState({
	        [e.target.id]: e.target.value
	      });
	}
	
	handleClick(e){

		var reqURL = '/users/' + this.props.item.todoList.user.id + '/todolists/' + this.props.item.todoList.id + '/items/' + this.props.item.id + '/' + this.state.selectItem + '/depitems';
		console.log(reqURL);
		var dependecy = {};
		fetch(reqURL, {
		    method: 'POST',
		    body: JSON.stringify(dependecy),
		    headers: {
		    	'Content-Type': 'application/json'
	        }   
		}).then(function (response) {
		   console.log("yeni dep eklendi");
			// refresh page
		   //location.reload()
		  
		   if(response.status == 403) {
			  response.text().then(function(msg) {
				  alert(msg);
				  				      
			  })
		   } else {
			   location.reload()
		   }
		   
		  
		})
				
	    
	    
	}
	
	componentDidMount() {	
		console.log("add dependency : ", this.props.todolist);
	}
		
	render() {
	
		var options = this.props.todolist.map(item =>
			<option key={item.id}>{item.id}</option>
		);
		
	
		return (<div className="form-inline">
				    <div className="form-group">
					<select  id="selectItem" onChange={this.handleChange}>
						<option></option>
						{options}
			        </select>
			    </div>
				    <div className="form-group">
				    	<button onClick={this.handleClick}>Add</button>
				    </div>
				</div>)
	}
	
}

class GetDependencies extends React.Component {

	constructor(props) {
		super(props);
		this.state = {depItems: []};
	}
	componentDidMount() {		
		

		var reqURL = '/users/' + this.props.item.todoList.user.id + '/todolists/' + this.props.item.todoList.id + '/items/' +  this.props.item.id + '/depitems';	
		client({method: 'GET', path: reqURL }).done(response => {
			this.setState({depItems: response.entity});
			console.log("deps : ", response.entity);
		});
		
	}
		
	render() {	
		var depItems = this.state.depItems.map(depItems =>
		<span key={depItems.id}>   {depItems.todoDepItem.id}</span>
		);
		return (<div>
					{depItems}
				</div>)
	}
	
}

class Item extends React.Component{
	
	constructor(props) {
		super(props);		
	}
	
	renderAddDependecy(item, todolist) {
		return <AddDependency item={item} todolist={todolist} />
	}
	
	renderDependency(item) {
		return <GetDependencies item={item} />;
	}
	
	
	renderDeleteItemButton(itemId) {
		return <DeleteItemButton ItemId={itemId} />;
	}
	
	renderItemStatusCheckbox(itemId, item) {
		return <ItemStatusCheckbox ItemId={itemId} item={item} />;
	}
	
	epochToDate(date) {
			var human_duedate = new Date(date);
			//console.log(human_duedate.getFullYear());
			var year = human_duedate.getFullYear();
			var month = human_duedate.getMonth() + 1;
			var day = human_duedate.getDate();
			console.log(day);
			return day + '/'+ month + '/' + year;
	}
	
	render() {
		return (
			<tr>
				<td>{this.props.item.id}</td>
				<td>{this.props.item.name}</td>
				<td>{this.epochToDate(this.props.item.epochTime)}</td>
				<td>
					{this.renderDeleteItemButton(this.props.item.id)}
				</td>
				<td>
					{this.renderItemStatusCheckbox(this.props.item.id,this.props.item)}
				</td>
				<td>{this.renderDependency(this.props.item)}</td>
				<td>
					{this.renderAddDependecy(this.props.item, this.props.todolist)}
			    </td>
			</tr>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)

