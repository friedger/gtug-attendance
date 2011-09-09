// Automatically Generated -- DO NOT EDIT
// be.niob.gtug.attendance.client.MyRequestFactory
package be.niob.gtug.attendance.client;
import java.util.Arrays;
import com.google.web.bindery.requestfactory.vm.impl.OperationData;
import com.google.web.bindery.requestfactory.vm.impl.OperationKey;
public final class MyRequestFactoryDeobfuscatorBuilder extends com.google.web.bindery.requestfactory.vm.impl.Deobfuscator.Builder {
{
withOperation(new OperationKey("j3Qhgh9yNAAlOZWxlsKuhVWtCKw="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()Ljava/lang/String;")
  .withMethodName("send")
  .withRequestContext("be.niob.gtug.attendance.client.MyRequestFactory$MessageRequest")
  .build());
withOperation(new OperationKey("d4Df$oWRBSrBLTpphvdpDo64mhw="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/lang/String;")
  .withMethodName("getMessage")
  .withRequestContext("be.niob.gtug.attendance.client.MyRequestFactory$HelloWorldRequest")
  .build());
withOperation(new OperationKey("qTfCqvfsTia9vwkJgsRIyP$8w8g="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/lang/String;")
  .withMethodName("getWortel")
  .withRequestContext("be.niob.gtug.attendance.client.MyRequestFactory$HelloWorldRequest")
  .build());
withOperation(new OperationKey("1_L4lDewif7MEHwPKg4PE8_sy5M="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()V")
  .withMethodName("register")
  .withRequestContext("be.niob.gtug.attendance.client.MyRequestFactory$RegistrationInfoRequest")
  .build());
withOperation(new OperationKey("lwfPPaqt1bN2KTZFcUp4BWaEApc="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()V")
  .withMethodName("unregister")
  .withRequestContext("be.niob.gtug.attendance.client.MyRequestFactory$RegistrationInfoRequest")
  .build());
withRawTypeToken("JUA18RAr6BzEl$FAqsC2wTNOAXk=", "be.niob.gtug.attendance.shared.MessageProxy");
withRawTypeToken("zoVGdkrdEGSpMaXQxPOTiITC$r0=", "be.niob.gtug.attendance.shared.RegistrationInfoProxy");
withRawTypeToken("8KVVbwaaAtl6KgQNlOTsLCp9TIU=", "com.google.web.bindery.requestfactory.shared.ValueProxy");
withRawTypeToken("FXHD5YU0TiUl3uBaepdkYaowx9k=", "com.google.web.bindery.requestfactory.shared.BaseProxy");
withClientToDomainMappings("be.niob.gtug.attendance.server.Message", Arrays.asList("be.niob.gtug.attendance.shared.MessageProxy"));
withClientToDomainMappings("be.niob.gtug.attendance.server.RegistrationInfo", Arrays.asList("be.niob.gtug.attendance.shared.RegistrationInfoProxy"));
}}
