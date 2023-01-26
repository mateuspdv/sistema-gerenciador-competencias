import { ContributorModule } from './modules/contributor/contributor.module';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { AppLayoutComponent } from "./layout/app.layout.component";

@NgModule({
    imports: [
        RouterModule.forRoot([
            {
                path: '', component: AppLayoutComponent,
                children: [
                    { path: 'competencia', loadChildren: () => import('./modules/competency/competency.module').then(m => m.CompetencyModule) },
                    { path: 'colaborador', loadChildren: () => import('./modules/contributor/contributor.module').then(m => m.ContributorModule) },
                ]
            }
        ], { scrollPositionRestoration: 'enabled', anchorScrolling: 'enabled', onSameUrlNavigation: 'reload' })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
