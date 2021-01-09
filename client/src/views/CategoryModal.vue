<template>
    <modal name="category-modal" transition="pop-out" :min-width="modalWidth" :min-height="257" :scrollable="true" :reset="true" height="auto">
        <div class="card card-container">
            <div class="card-header">
                <h3>Добавление категории</h3>
            </div>
            <div class="card-body">
                <label for="categoryTitle">Категория</label>
                <input type="text" v-model="categoryTitle" class="form-control" name="categoryTitle" v-validate="'required'"/>
                <div
                        v-if="errors.has('categoryTitle')"
                        class="alert alert-danger"
                        role="alert"
                >Категория обязательна</div>
            </div>
            <div class="card-footer form-group row justify-content-start">
                <button v-on:click="createCategory" class="col-sm-6 form-control">Добавить</button>
                <button v-on:click="$modal.hide('category-modal')" class="col-sm-6 form-control">Закрыть</button>
            </div>
        </div>
    </modal>
</template>
<script>
    import CategoryService from '../services/category.service';

    const MODAL_WIDTH = 656;
    const MODAL_HEIGHT = 257;

    export default {
        name: 'CategoryModal',
        data() {
            return {
                categoryTitle : null,
                modalWidth : MODAL_WIDTH,
                modalHeight : MODAL_HEIGHT
            }
        },
        created() {
            this.modalWidth = window.innerWidth < MODAL_WIDTH ? MODAL_WIDTH / 2 : MODAL_WIDTH;
            this.modalHeight = window.innerHeight < MODAL_HEIGHT ? MODAL_HEIGHT / 2 : MODAL_HEIGHT;
        },
        methods: {
            createCategory() {
                const me = this;
                this.$validator.validateAll().then(isValid => {
                    if (!isValid) {
                        return;
                    }
                    CategoryService.createCategory({name : me.categoryTitle})
                        .then(() => me.$modal.hide('category-modal'))
                })
            }
        }
    }
</script>
<style>
</style>