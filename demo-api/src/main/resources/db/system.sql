--------------------------------------------------
-- Export file for user TL_PT_DEMO              --
-- Created by xieyanling on 2020/7/23, 18:50:24 --
--------------------------------------------------

set define off
spool system.log

prompt
prompt Creating table SYS_ANNEX
prompt ========================
prompt
create table SYS_ANNEX
(
  annex_id    VARCHAR2(32) not null,
  annex_name  VARCHAR2(500),
  annex_type  VARCHAR2(32),
  annex_dir   VARCHAR2(2000),
  rel_id      VARCHAR2(32),
  annex_no    VARCHAR2(200),
  order_no    NUMBER(6),
  create_user VARCHAR2(32),
  update_user VARCHAR2(32),
  create_time DATE,
  update_time DATE,
  file_title  VARCHAR2(32)
)
;
comment on table SYS_ANNEX
  is '附件表';
comment on column SYS_ANNEX.annex_id
  is '主键ID';
comment on column SYS_ANNEX.annex_name
  is '附件名称';
comment on column SYS_ANNEX.annex_type
  is '附件类型';
comment on column SYS_ANNEX.annex_dir
  is '附件地址';
comment on column SYS_ANNEX.rel_id
  is '关联ID';
comment on column SYS_ANNEX.annex_no
  is '附件编号';
comment on column SYS_ANNEX.order_no
  is '附件排序';
comment on column SYS_ANNEX.create_user
  is '创建人';
comment on column SYS_ANNEX.update_user
  is '更新人';
comment on column SYS_ANNEX.create_time
  is '创建时间';
comment on column SYS_ANNEX.update_time
  is '更新时间';
comment on column SYS_ANNEX.file_title
  is '附件主题，见字典表附件主题FILE_TITLE';
alter table SYS_ANNEX
  add constraint PK_SYS_ANNEX primary key (ANNEX_ID);

prompt
prompt Creating table SYS_OPERATE_LOG
prompt ==============================
prompt
create table SYS_OPERATE_LOG
(
  log_id       VARCHAR2(32) not null,
  operate__ip  VARCHAR2(16),
  operator     VARCHAR2(32),
  operate_time DATE,
  content      VARCHAR2(500)
)
;
comment on table SYS_OPERATE_LOG
  is '系统操作业务日志';
comment on column SYS_OPERATE_LOG.log_id
  is '主键';
comment on column SYS_OPERATE_LOG.operate__ip
  is '操作IP';
comment on column SYS_OPERATE_LOG.operator
  is '操作人';
comment on column SYS_OPERATE_LOG.operate_time
  is '操作时间';
comment on column SYS_OPERATE_LOG.content
  is '操作内容';
alter table SYS_OPERATE_LOG
  add constraint PK_SYS_OPERATE_LOG primary key (LOG_ID);

prompt
prompt Creating table SYS_QUARTZ_JOB
prompt =============================
prompt
create table SYS_QUARTZ_JOB
(
  id              VARCHAR2(32) not null,
  bean_name       VARCHAR2(255),
  cron_expression VARCHAR2(255),
  is_pause        NUMBER(1),
  job_name        VARCHAR2(255),
  method_name     VARCHAR2(255),
  params          VARCHAR2(255),
  remark          VARCHAR2(255),
  create_time     DATE,
  update_time     DATE,
  create_user     VARCHAR2(255),
  update_user     VARCHAR2(255)
)
;
comment on table SYS_QUARTZ_JOB
  is '调度任务';
comment on column SYS_QUARTZ_JOB.bean_name
  is 'Spring Bean名称';
comment on column SYS_QUARTZ_JOB.cron_expression
  is 'cron 表达式';
comment on column SYS_QUARTZ_JOB.is_pause
  is '状态：1暂停、0启用';
comment on column SYS_QUARTZ_JOB.job_name
  is '任务名称';
comment on column SYS_QUARTZ_JOB.method_name
  is '方法名称';
comment on column SYS_QUARTZ_JOB.params
  is '参数';
comment on column SYS_QUARTZ_JOB.remark
  is '备注';
comment on column SYS_QUARTZ_JOB.create_time
  is '创建时间';
comment on column SYS_QUARTZ_JOB.update_time
  is '创建或更新日期';
comment on column SYS_QUARTZ_JOB.create_user
  is '创建人';
comment on column SYS_QUARTZ_JOB.update_user
  is '更新人';
alter table SYS_QUARTZ_JOB
  add constraint PK_SYS_QUARTZ_JOB primary key (ID);

prompt
prompt Creating table SYS_QUARTZ_LOG
prompt =============================
prompt
create table SYS_QUARTZ_LOG
(
  id               VARCHAR2(32) not null,
  job_id           VARCHAR2(32),
  bean_name        VARCHAR2(255),
  cron_expression  VARCHAR2(255),
  exception_detail CLOB,
  is_success       NUMBER(1),
  job_name         VARCHAR2(255),
  method_name      VARCHAR2(255),
  params           VARCHAR2(255),
  time             NUMBER(22),
  create_time      DATE,
  update_time      DATE,
  create_user      VARCHAR2(255),
  update_user      VARCHAR2(255)
)
;
comment on table SYS_QUARTZ_LOG
  is '调度任务日志';
comment on column SYS_QUARTZ_LOG.job_id
  is '任务ID,关联JOB表主键';
comment on column SYS_QUARTZ_LOG.bean_name
  is 'Bean名称';
comment on column SYS_QUARTZ_LOG.cron_expression
  is 'cron表达式';
comment on column SYS_QUARTZ_LOG.exception_detail
  is '异常详细';
comment on column SYS_QUARTZ_LOG.is_success
  is '是否成功';
comment on column SYS_QUARTZ_LOG.job_name
  is '任务名称';
comment on column SYS_QUARTZ_LOG.method_name
  is '方法名称';
comment on column SYS_QUARTZ_LOG.params
  is '参数';
comment on column SYS_QUARTZ_LOG.time
  is '耗时（毫秒）';
comment on column SYS_QUARTZ_LOG.create_time
  is '创建时间';
comment on column SYS_QUARTZ_LOG.update_time
  is '更新时间';
comment on column SYS_QUARTZ_LOG.create_user
  is '创建人';
comment on column SYS_QUARTZ_LOG.update_user
  is '更新人';
alter table SYS_QUARTZ_LOG
  add constraint PK_SYS_QUARTZ_LOG primary key (ID);


spool off
