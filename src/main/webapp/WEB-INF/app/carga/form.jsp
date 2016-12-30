


<div class="users-section" ng-cloak>
    <form name="userForm" ng-submit="submitSearch()">
    <div class="modal-form" style="max-width: 100%">
        <md-toolbar style="padding: 20px 10px 0px 10px; min-height: 0;">
            <div class="md-toolbar-tools">            
                <h2>BÚSQUEDA</h2>
                <md-button class="md-icon-button btn-close" ng-click="cancel()">
                    <md-icon aria-label="Close dialog" class="material-icons" ng-click="close()">close</md-icon>
                </md-button>
            </div>
        </md-toolbar>         
    </div>     
            
    <div layout="row" layout-align="start" style="width: 790px; margin: 10px 30px;">
        <div flex="30" style="margin-top: 30px;padding-right: 10px;border-right: 1px solid #000;margin-right: 10px;">
            <md-input-container flex="100" class="md-icon-float md-block">
                 <label>Número de DNI...</label>
                 <input name="dni" ng-model="padronSearch.numele" type="text" maxlength="8">                    
             </md-input-container>

             <md-input-container flex="100" class="md-icon-float md-block">
                 <label>Apellido Paterno...</label>
                 <input uppercase-only name="paterno" ng-model="padronSearch.apePat" type="text">                    
             </md-input-container>

             <md-input-container flex="100" class="md-icon-float md-block">
                 <label>Apellido Materno...</label>
                 <input uppercase-only name="materno" ng-model="padronSearch.apMat" type="text">                    
             </md-input-container>

             <md-input-container flex="100" class="md-icon-float md-block">
                 <label>Nombres...</label>
                 <input uppercase-only name="nombre" ng-model="padronSearch.nombres" type="text">                    
             </md-input-container>
        </div>        

        <md-content flex="70">
          <md-list style="padding-top: 0;">
            <md-subheader style="padding-top: 0; font-size: 17px;color: #005780;background-color: #fff;" class="md-no-sticky">RESULTADOS DE BÚSQUEDA</md-subheader>
            <div style="height: 370px;overflow-y: auto;">
            <md-list-item style="border-bottom: 4px solid #fff;" class="md-2-line" ng-repeat="padronSearch in padrones">
              <div style="width: 20px; margin: auto;">
                  <p>[[$index + 1]]</p>
              </div>    
              <div style="width: 15%;border-left: 4px solid #fff;margin: auto; padding: 5px;">
                  <p>[[padronSearch.numEle]]</p>
              </div>        
              <div style="width: 30%;border-left: 4px solid #fff;" class="md-list-item-text">
                  <h3>[[padronSearch.apePat+' '+padronSearch.apMat+', '+padronSearch.nombres]]</h3>
                  <p>Ubigeo:[[padronSearch.ubigeo]]</p>
              </div>
                <md-button  ng-click="edit(padronSearch)" style="border: 1px solid #005780; color: #005780;" class="md-secondary">Remplazar</md-button>
              <md-divider ng-if="!$last"></md-divider>
            </md-list-item>
            </div>            
          </md-list>
        </md-content>        
        
    </div>
    <div class="layout action-btn" layout-align="left">
        <md-button type="submit" class="md-raised btn-default" >BUSCAR</md-button>
    </div>         
</form>         
</div>                          
