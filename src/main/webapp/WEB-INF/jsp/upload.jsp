<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<c:if test="${(empty compte)}">
<jsp:forward page="/welcome.do"></jsp:forward>
</c:if>
<script src='js/upload.js'> </script>
<script src='dwr/interface/UploadMonitor.js'> </script>
<script src='dwr/engine.js'> </script>
<script src='dwr/util.js'> </script>

<style type="text/css">

#progressBar {
	padding-top: 5px;
}

#progressBarBox {
	width: 350px;
	height: 20px;
	border: 1px inset;
	background: #eee;
}

#progressBarBoxContent {
	width: 0;
	height: 20px;
	border-right: 1px solid #444;
	background: #9ACB34;
}
</style>
<script language="javascript">
        function updateStatusMessage(message){
          DWRUtil.setValue('updateStatusMsg', message);
        }
     </script>
<div>
<table width="100%" style="background-color: #FFFFFF; padding: 5px;">
	<tr>
		<td>
		<p>
		<h1>Upload de fichiers</h1>
		</p>
		<a href="goHome.do">retour</a> <iframe
			src="${action}.do?idFolder=${idFolder}" name="addFileFrame"
			id="addFileFrame" frameborder="0" scrolling='no' width="100%"
			height="500px;"></iframe> <br />
		<p><span align='left' class='status-text' id='updateStatusMsg'></span>
		<div id="progressBar" style="display: none;">

		<div id="theMeter">
		<div id="progressBarText"></div>

		<div id="progressBarBox">
		<div id="progressBarBoxContent"></div>
		</div>
		</div>
		</div>
		</p>
		</td>
	</tr>
</table>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>