import win32com.client
import mysql.connector
import time
conn2mysql = mysql.connector.connect(user='root', password='19921218', database='cecweb', use_unicode=True)
conn = win32com.client.Dispatch(r'ADODB.Connection')
DSN = r'PROVIDER=Microsoft.Jet.OLEDB.4.0;Persist Security Info=False;DATA SOURCE=e:/cec.mdb;'   
rs = win32com.client.Dispatch(r'ADODB.Recordset')
conn.Open(DSN)

#copy column
rs.Open('[select * from Aclass]',conn,1,3)
rs.MoveFirst()
count=0
print 'begin copy data Aclass(old database) to art_column(new database)....'
cursor = conn2mysql.cursor()
while not rs.EOF:
    count = count + 1
    record = []
    for i in range(rs.Fields.count):
        record.append(rs.Fields.Item(i).Value)
    if(record[11]==0):
        record[11]=None
        data = (record[0],record[1],0,record[3],None,False,None,record[25],record[21],record[20])
        cursor.execute('insert into art_column(columnId,col_name,col_level,art_count,parent_col_id,is_address,out_address,is_new_windows,is_nav,is_index) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)',data)
    rs.MoveNext()
conn2mysql.commit()

#copy columnChild
rs.MoveFirst()
count=0
print 'begin copy data Aclass(old database) to art_column(new database)....'
cursor = conn2mysql.cursor()
while not rs.EOF:
    count = count + 1
    record = []
    for i in range(rs.Fields.count):
        record.append(rs.Fields.Item(i).Value)
    if(record[11]!=0):
        data = (record[0],record[1],0,record[3],record[11],False,None,record[25],record[21],record[20])
        cursor.execute('insert into art_column(columnId,col_name,col_level,art_count,parent_col_id,is_address,out_address,is_new_windows,is_nav,is_index) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)',data)
    rs.MoveNext()
conn2mysql.commit()

print 'column child copy over.'+str(count)+' counts has copyed'

#copy user
print 'begin copy data member(old database) to users(new database)....'
rs = win32com.client.Dispatch(r'ADODB.Recordset')
rs.Open('[select * from member]',conn,1,3)
rs.MoveFirst()
count=0
while not rs.EOF:
     count = count + 1
     record = []
     for i in range(rs.Fields.count):
         record.append(rs.Fields.Item(i).Value)
     registTime = str(record[6])[0:6]+"20"+str(record[6])[6:]
     rgtime = time.mktime(time.strptime(registTime,r'%m/%d/%Y %H:%M:%S'))
     data = (record[0],record[1],record[1],record[2],0,time.localtime(rgtime),record[28],None,None,record[7],None)
     cursor.execute('insert into users(userId,userName,nickName,password,u_level,reg_time,login_count,last_time,last_ip,art_count,remark)  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)',data)
     rs.MoveNext()
# conn2mysql.commit()
print 'users copy over.'+str(count)+' counts has copyed'

#copy article
print 'begin copy data article(old database) to article(new database)....'
rs = win32com.client.Dispatch(r'ADODB.Recordset')
rs.Open('[select * from article where articleid<6000]',conn,1,3)
rs.MoveFirst()
count=0
while not rs.EOF:
    count = count + 1
    record = []
    for i in range(rs.Fields.count):
        record.append(rs.Fields.Item(i).Value)
    registTime = str(record[6])[0:6]+"20"+str(record[6])[6:]
    publishTime = time.mktime(time.strptime(registTime,r'%m/%d/%Y %H:%M:%S'))
    if(record[20]==None):
        record[20]='null'
    if(record[1]==None):
        record[1]=""
    data = (record[0],record[2],record[1],False,None,record[12],record[3],record[14],record[20],False,False,record[25],time.localtime(publishTime),record[5])
    cursor.execute('insert into article(artId,title,content,is_address,out_address,title_pic,columnId,summary,author,is_top,is_colmun_top,is_index_top,public_time,read_count)  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)',data)
    rs.MoveNext()
conn2mysql.commit()
print 'article copy over.'+str(count)+' counts has copyed'

cursor.close()
conn2mysql.close()
conn.Close()


