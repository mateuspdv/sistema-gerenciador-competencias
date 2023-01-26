import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html'
})
export class AppMenuComponent implements OnInit {

    model: any[] = [];

    constructor(public layoutService: LayoutService) { }

    ngOnInit() {
        this.model = [
            {
                label: 'Funcionalidades',
                items: [
                    { label: 'Competência', icon: 'pi pi-fw pi-book', routerLink: ['/competencia'] },
                    { label: 'Colaborador', icon: 'pi pi-fw pi-user', routerLink: ['/colaborador'] },
                    { label: 'Turma', icon: 'pi pi-fw pi-users', routerLink: ['/uikit/floatlabel'] }
                ]
            }
        ];
    }
}
