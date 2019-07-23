// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: msg.proto

#ifndef PROTOBUF_msg_2eproto__INCLUDED
#define PROTOBUF_msg_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 3000000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 3000000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/metadata.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)

// Internal implementation detail -- do not call these.
void protobuf_AddDesc_msg_2eproto();
void protobuf_AssignDesc_msg_2eproto();
void protobuf_ShutdownFile_msg_2eproto();

class Head;
class Msg;

// ===================================================================

class Msg : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:Msg) */ {
 public:
  Msg();
  virtual ~Msg();

  Msg(const Msg& from);

  inline Msg& operator=(const Msg& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const Msg& default_instance();

  void Swap(Msg* other);

  // implements Message ----------------------------------------------

  inline Msg* New() const { return New(NULL); }

  Msg* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const Msg& from);
  void MergeFrom(const Msg& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      bool deterministic, ::google::protobuf::uint8* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const {
    return InternalSerializeWithCachedSizesToArray(false, output);
  }
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  void InternalSwap(Msg* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return _internal_metadata_.arena();
  }
  inline void* MaybeArenaPtr() const {
    return _internal_metadata_.raw_arena_ptr();
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // optional .Head head = 1;
  bool has_head() const;
  void clear_head();
  static const int kHeadFieldNumber = 1;
  const ::Head& head() const;
  ::Head* mutable_head();
  ::Head* release_head();
  void set_allocated_head(::Head* head);

  // optional string body = 2;
  void clear_body();
  static const int kBodyFieldNumber = 2;
  const ::std::string& body() const;
  void set_body(const ::std::string& value);
  void set_body(const char* value);
  void set_body(const char* value, size_t size);
  ::std::string* mutable_body();
  ::std::string* release_body();
  void set_allocated_body(::std::string* body);

  // @@protoc_insertion_point(class_scope:Msg)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  bool _is_default_instance_;
  ::Head* head_;
  ::google::protobuf::internal::ArenaStringPtr body_;
  mutable int _cached_size_;
  friend void  protobuf_AddDesc_msg_2eproto();
  friend void protobuf_AssignDesc_msg_2eproto();
  friend void protobuf_ShutdownFile_msg_2eproto();

  void InitAsDefaultInstance();
  static Msg* default_instance_;
};
// -------------------------------------------------------------------

class Head : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:Head) */ {
 public:
  Head();
  virtual ~Head();

  Head(const Head& from);

  inline Head& operator=(const Head& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const Head& default_instance();

  void Swap(Head* other);

  // implements Message ----------------------------------------------

  inline Head* New() const { return New(NULL); }

  Head* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const Head& from);
  void MergeFrom(const Head& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      bool deterministic, ::google::protobuf::uint8* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const {
    return InternalSerializeWithCachedSizesToArray(false, output);
  }
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  void InternalSwap(Head* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return _internal_metadata_.arena();
  }
  inline void* MaybeArenaPtr() const {
    return _internal_metadata_.raw_arena_ptr();
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // optional string msgId = 1;
  void clear_msgid();
  static const int kMsgIdFieldNumber = 1;
  const ::std::string& msgid() const;
  void set_msgid(const ::std::string& value);
  void set_msgid(const char* value);
  void set_msgid(const char* value, size_t size);
  ::std::string* mutable_msgid();
  ::std::string* release_msgid();
  void set_allocated_msgid(::std::string* msgid);

  // optional int32 msgType = 2;
  void clear_msgtype();
  static const int kMsgTypeFieldNumber = 2;
  ::google::protobuf::int32 msgtype() const;
  void set_msgtype(::google::protobuf::int32 value);

  // optional int32 msgContentType = 3;
  void clear_msgcontenttype();
  static const int kMsgContentTypeFieldNumber = 3;
  ::google::protobuf::int32 msgcontenttype() const;
  void set_msgcontenttype(::google::protobuf::int32 value);

  // optional string fromId = 4;
  void clear_fromid();
  static const int kFromIdFieldNumber = 4;
  const ::std::string& fromid() const;
  void set_fromid(const ::std::string& value);
  void set_fromid(const char* value);
  void set_fromid(const char* value, size_t size);
  ::std::string* mutable_fromid();
  ::std::string* release_fromid();
  void set_allocated_fromid(::std::string* fromid);

  // optional string toId = 5;
  void clear_toid();
  static const int kToIdFieldNumber = 5;
  const ::std::string& toid() const;
  void set_toid(const ::std::string& value);
  void set_toid(const char* value);
  void set_toid(const char* value, size_t size);
  ::std::string* mutable_toid();
  ::std::string* release_toid();
  void set_allocated_toid(::std::string* toid);

  // optional int64 timestamp = 6;
  void clear_timestamp();
  static const int kTimestampFieldNumber = 6;
  ::google::protobuf::int64 timestamp() const;
  void set_timestamp(::google::protobuf::int64 value);

  // optional int32 statusReport = 7;
  void clear_statusreport();
  static const int kStatusReportFieldNumber = 7;
  ::google::protobuf::int32 statusreport() const;
  void set_statusreport(::google::protobuf::int32 value);

  // optional string extend = 8;
  void clear_extend();
  static const int kExtendFieldNumber = 8;
  const ::std::string& extend() const;
  void set_extend(const ::std::string& value);
  void set_extend(const char* value);
  void set_extend(const char* value, size_t size);
  ::std::string* mutable_extend();
  ::std::string* release_extend();
  void set_allocated_extend(::std::string* extend);

  // @@protoc_insertion_point(class_scope:Head)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  bool _is_default_instance_;
  ::google::protobuf::internal::ArenaStringPtr msgid_;
  ::google::protobuf::int32 msgtype_;
  ::google::protobuf::int32 msgcontenttype_;
  ::google::protobuf::internal::ArenaStringPtr fromid_;
  ::google::protobuf::internal::ArenaStringPtr toid_;
  ::google::protobuf::int64 timestamp_;
  ::google::protobuf::internal::ArenaStringPtr extend_;
  ::google::protobuf::int32 statusreport_;
  mutable int _cached_size_;
  friend void  protobuf_AddDesc_msg_2eproto();
  friend void protobuf_AssignDesc_msg_2eproto();
  friend void protobuf_ShutdownFile_msg_2eproto();

  void InitAsDefaultInstance();
  static Head* default_instance_;
};
// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// Msg

// optional .Head head = 1;
inline bool Msg::has_head() const {
  return !_is_default_instance_ && head_ != NULL;
}
inline void Msg::clear_head() {
  if (GetArenaNoVirtual() == NULL && head_ != NULL) delete head_;
  head_ = NULL;
}
inline const ::Head& Msg::head() const {
  // @@protoc_insertion_point(field_get:Msg.head)
  return head_ != NULL ? *head_ : *default_instance_->head_;
}
inline ::Head* Msg::mutable_head() {
  
  if (head_ == NULL) {
    head_ = new ::Head;
  }
  // @@protoc_insertion_point(field_mutable:Msg.head)
  return head_;
}
inline ::Head* Msg::release_head() {
  // @@protoc_insertion_point(field_release:Msg.head)
  
  ::Head* temp = head_;
  head_ = NULL;
  return temp;
}
inline void Msg::set_allocated_head(::Head* head) {
  delete head_;
  head_ = head;
  if (head) {
    
  } else {
    
  }
  // @@protoc_insertion_point(field_set_allocated:Msg.head)
}

// optional string body = 2;
inline void Msg::clear_body() {
  body_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Msg::body() const {
  // @@protoc_insertion_point(field_get:Msg.body)
  return body_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Msg::set_body(const ::std::string& value) {
  
  body_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:Msg.body)
}
inline void Msg::set_body(const char* value) {
  
  body_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:Msg.body)
}
inline void Msg::set_body(const char* value, size_t size) {
  
  body_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:Msg.body)
}
inline ::std::string* Msg::mutable_body() {
  
  // @@protoc_insertion_point(field_mutable:Msg.body)
  return body_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Msg::release_body() {
  // @@protoc_insertion_point(field_release:Msg.body)
  
  return body_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Msg::set_allocated_body(::std::string* body) {
  if (body != NULL) {
    
  } else {
    
  }
  body_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), body);
  // @@protoc_insertion_point(field_set_allocated:Msg.body)
}

// -------------------------------------------------------------------

// Head

// optional string msgId = 1;
inline void Head::clear_msgid() {
  msgid_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Head::msgid() const {
  // @@protoc_insertion_point(field_get:Head.msgId)
  return msgid_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Head::set_msgid(const ::std::string& value) {
  
  msgid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:Head.msgId)
}
inline void Head::set_msgid(const char* value) {
  
  msgid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:Head.msgId)
}
inline void Head::set_msgid(const char* value, size_t size) {
  
  msgid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:Head.msgId)
}
inline ::std::string* Head::mutable_msgid() {
  
  // @@protoc_insertion_point(field_mutable:Head.msgId)
  return msgid_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Head::release_msgid() {
  // @@protoc_insertion_point(field_release:Head.msgId)
  
  return msgid_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Head::set_allocated_msgid(::std::string* msgid) {
  if (msgid != NULL) {
    
  } else {
    
  }
  msgid_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), msgid);
  // @@protoc_insertion_point(field_set_allocated:Head.msgId)
}

// optional int32 msgType = 2;
inline void Head::clear_msgtype() {
  msgtype_ = 0;
}
inline ::google::protobuf::int32 Head::msgtype() const {
  // @@protoc_insertion_point(field_get:Head.msgType)
  return msgtype_;
}
inline void Head::set_msgtype(::google::protobuf::int32 value) {
  
  msgtype_ = value;
  // @@protoc_insertion_point(field_set:Head.msgType)
}

// optional int32 msgContentType = 3;
inline void Head::clear_msgcontenttype() {
  msgcontenttype_ = 0;
}
inline ::google::protobuf::int32 Head::msgcontenttype() const {
  // @@protoc_insertion_point(field_get:Head.msgContentType)
  return msgcontenttype_;
}
inline void Head::set_msgcontenttype(::google::protobuf::int32 value) {
  
  msgcontenttype_ = value;
  // @@protoc_insertion_point(field_set:Head.msgContentType)
}

// optional string fromId = 4;
inline void Head::clear_fromid() {
  fromid_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Head::fromid() const {
  // @@protoc_insertion_point(field_get:Head.fromId)
  return fromid_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Head::set_fromid(const ::std::string& value) {
  
  fromid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:Head.fromId)
}
inline void Head::set_fromid(const char* value) {
  
  fromid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:Head.fromId)
}
inline void Head::set_fromid(const char* value, size_t size) {
  
  fromid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:Head.fromId)
}
inline ::std::string* Head::mutable_fromid() {
  
  // @@protoc_insertion_point(field_mutable:Head.fromId)
  return fromid_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Head::release_fromid() {
  // @@protoc_insertion_point(field_release:Head.fromId)
  
  return fromid_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Head::set_allocated_fromid(::std::string* fromid) {
  if (fromid != NULL) {
    
  } else {
    
  }
  fromid_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), fromid);
  // @@protoc_insertion_point(field_set_allocated:Head.fromId)
}

// optional string toId = 5;
inline void Head::clear_toid() {
  toid_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Head::toid() const {
  // @@protoc_insertion_point(field_get:Head.toId)
  return toid_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Head::set_toid(const ::std::string& value) {
  
  toid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:Head.toId)
}
inline void Head::set_toid(const char* value) {
  
  toid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:Head.toId)
}
inline void Head::set_toid(const char* value, size_t size) {
  
  toid_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:Head.toId)
}
inline ::std::string* Head::mutable_toid() {
  
  // @@protoc_insertion_point(field_mutable:Head.toId)
  return toid_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Head::release_toid() {
  // @@protoc_insertion_point(field_release:Head.toId)
  
  return toid_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Head::set_allocated_toid(::std::string* toid) {
  if (toid != NULL) {
    
  } else {
    
  }
  toid_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), toid);
  // @@protoc_insertion_point(field_set_allocated:Head.toId)
}

// optional int64 timestamp = 6;
inline void Head::clear_timestamp() {
  timestamp_ = GOOGLE_LONGLONG(0);
}
inline ::google::protobuf::int64 Head::timestamp() const {
  // @@protoc_insertion_point(field_get:Head.timestamp)
  return timestamp_;
}
inline void Head::set_timestamp(::google::protobuf::int64 value) {
  
  timestamp_ = value;
  // @@protoc_insertion_point(field_set:Head.timestamp)
}

// optional int32 statusReport = 7;
inline void Head::clear_statusreport() {
  statusreport_ = 0;
}
inline ::google::protobuf::int32 Head::statusreport() const {
  // @@protoc_insertion_point(field_get:Head.statusReport)
  return statusreport_;
}
inline void Head::set_statusreport(::google::protobuf::int32 value) {
  
  statusreport_ = value;
  // @@protoc_insertion_point(field_set:Head.statusReport)
}

// optional string extend = 8;
inline void Head::clear_extend() {
  extend_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& Head::extend() const {
  // @@protoc_insertion_point(field_get:Head.extend)
  return extend_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Head::set_extend(const ::std::string& value) {
  
  extend_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:Head.extend)
}
inline void Head::set_extend(const char* value) {
  
  extend_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:Head.extend)
}
inline void Head::set_extend(const char* value, size_t size) {
  
  extend_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:Head.extend)
}
inline ::std::string* Head::mutable_extend() {
  
  // @@protoc_insertion_point(field_mutable:Head.extend)
  return extend_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* Head::release_extend() {
  // @@protoc_insertion_point(field_release:Head.extend)
  
  return extend_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void Head::set_allocated_extend(::std::string* extend) {
  if (extend != NULL) {
    
  } else {
    
  }
  extend_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), extend);
  // @@protoc_insertion_point(field_set_allocated:Head.extend)
}

#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS
// -------------------------------------------------------------------


// @@protoc_insertion_point(namespace_scope)

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_msg_2eproto__INCLUDED
