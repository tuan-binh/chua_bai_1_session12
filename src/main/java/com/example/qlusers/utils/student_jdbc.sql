use jdbc_user;

create table Students
(
    id       int auto_increment primary key,
    fullName varchar(100) not null,
    email    varchar(100) not null unique,
    address varchar(255) not null,
    phone    varchar(11)  not null unique,
    status   bit

);

# stored procedure lấy ra tất cả students
delimiter //
create procedure PROC_GET_ALL_USER()
begin
select *
from students;
end//

# stored procedure tìm ra thông qua id
delimiter //
create procedure PROC_GET_BY_ID(idFind int)
begin
select * from students where id = idFind;
end //
# stored procedure thêm mới
delimiter //
create procedure PROC_ADD_STUDENT(newFullName varchar(100), newEmail varchar(100), newAddress varchar(100),
                                  newPhone varchar(11), newStatus bit)
begin
insert into students (fullName, email, address, phone, status)
values (newFullName, newEmail, newAddress, newPhone, newStatus);
end //
# stored procedure cập nhật
delimiter //
create procedure PROC_UPDATE_STUDENT(newFullName varchar(100), newEmail varchar(100), newAddress varchar(100),
                                     newPhone varchar(11), newStatus bit, idUpdate int)
begin
update students
set fullName = newFullName,
    email    = newEmail,
    address  = newAddress,
    phone    = newPhone,
    status   = newStatus
where id = idUpdate;
end //
# stored procedure xóa
delimiter //
create procedure PROC_DELETE_STUDENT(idDelete int)
begin
delete from students where id = idDelete;
end //
# stored procedure tìm kiếm
delimiter //
create procedure PROC_SEARCH_BY_NAME(keyword varchar(255))
begin
select * from students where fullName like concat('%', keyword, '%');
end //
