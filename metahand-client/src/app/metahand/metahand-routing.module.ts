import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { SparqlComponent } from './components/sparql/sparql.component';

const routes: Routes = [
    { path: '', component: SparqlComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MetahandRoutingModule { }